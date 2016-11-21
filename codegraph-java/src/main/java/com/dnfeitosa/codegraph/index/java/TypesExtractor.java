package com.dnfeitosa.codegraph.index.java;

import com.dnfeitosa.codegraph.client.resources.Field;
import com.dnfeitosa.codegraph.client.resources.Method;
import com.dnfeitosa.codegraph.client.resources.Type;
import com.dnfeitosa.codegraph.index.java.internal.CompilationUnitPackageResolver;
import com.dnfeitosa.codegraph.index.java.internal.PackageResolver;
import com.dnfeitosa.codegraph.index.java.internal.TypeReferenceConverter;
import com.dnfeitosa.codegraph.index.java.internal.javaparser.ConstructorsCollector;
import com.dnfeitosa.codegraph.index.java.internal.javaparser.FieldsCollector;
import com.dnfeitosa.codegraph.index.java.internal.javaparser.MethodsCollector;
import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.body.EnumDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.body.TypeDeclaration;
import com.github.javaparser.ast.type.ClassOrInterfaceType;

import java.io.File;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static java.util.stream.IntStream.range;

public class TypesExtractor {

    private final TypeReferenceConverter typeReferenceConverter;

    public TypesExtractor() {
        this.typeReferenceConverter = new TypeReferenceConverter();
    }

    public List<Type> parseTypes(File file, PackageResolver basePackageResolver) {
        CompilationUnit compilationUnit = getCompilationUnitFrom(file);

        PackageResolver packageResolver = new CompilationUnitPackageResolver(compilationUnit, basePackageResolver);
        return compilationUnit.getTypes().stream()
                .map(type -> toTypeReference(type, compilationUnit, packageResolver))
                .collect(toList());
    }

    private Type toTypeReference(TypeDeclaration typeDeclaration, CompilationUnit compilationUnit, PackageResolver packageResolver) {
        Type type = new Type();
        addBasicData(type, compilationUnit, typeDeclaration);
        addMethods(type, typeDeclaration, packageResolver);
        addFields(type, typeDeclaration, packageResolver);
        addSuperclass(type, typeDeclaration, packageResolver);
        addInterfaces(type, typeDeclaration, packageResolver);
        return type;
    }

    private void addInterfaces(Type type, TypeDeclaration typeDeclaration, PackageResolver packageResolver) {
        if (!(typeDeclaration instanceof ClassOrInterfaceDeclaration)) {
            return;
        }
        ClassOrInterfaceDeclaration coid = (ClassOrInterfaceDeclaration) typeDeclaration;
        getInterfacesFrom(coid).forEach(i -> type.addInterface(toTypeReference(i, packageResolver)));
    }

    private List<ClassOrInterfaceType> getInterfacesFrom(ClassOrInterfaceDeclaration coid) {
        return coid.isInterface()
                ? coid.getExtends()
                : coid.getImplements();
    }

    private void addSuperclass(Type type, TypeDeclaration typeDeclaration, PackageResolver packageResolver) {
        if (!(typeDeclaration instanceof ClassOrInterfaceDeclaration)) {
            return;
        }
        ClassOrInterfaceDeclaration coid = (ClassOrInterfaceDeclaration) typeDeclaration;
        if (coid.getExtends().isEmpty() || coid.isInterface()) {
            return;
        }

        ClassOrInterfaceType classOrInterfaceType = coid.getExtends().get(0);
        type.setSuperclass(toTypeReference(classOrInterfaceType, packageResolver));
    }

    private void addFields(Type type, TypeDeclaration typeDeclaration, PackageResolver packageResolver) {
        collectFieldsFrom(typeDeclaration).forEach(fieldDeclaration -> {
            String fieldName = fieldDeclaration.getVariables().get(0).getId().getName();
            Field field = new Field(fieldName, toTypeReference(fieldDeclaration.getType(), packageResolver));
            type.addField(field);
        });
    }

    private void addMethods(Type type, TypeDeclaration typeDeclaration, PackageResolver packageResolver) {
        collectConstructorsFrom(typeDeclaration).forEach(constructorDeclaration -> {
            Method method = new Method(constructorDeclaration.getName());
            extractParameters(packageResolver, method, constructorDeclaration.getParameters());
            type.addMethod(method);
        });
        collectMethodsFrom(typeDeclaration).forEach(methodDeclaration -> {
            Method method = new Method(methodDeclaration.getName());
            extractParameters(packageResolver, method, methodDeclaration.getParameters());
            extractReturnTypes(packageResolver, method, methodDeclaration);
            type.addMethod(method);
        });
    }

    private void extractReturnTypes(PackageResolver packageResolver, Method method, MethodDeclaration methodDeclaration) {
        com.github.javaparser.ast.type.Type type = methodDeclaration.getType();
        method.addReturnType(toTypeReference(type, packageResolver));
    }

    private void extractParameters(PackageResolver packageResolver, Method method, List<Parameter> parameters) {
        range(0, parameters.size()).forEach(index -> {
            Parameter p = parameters.get(index);
            method.addParameter(index, toTypeReference(p.getType(), packageResolver));
        });
    }

    private Type toTypeReference(com.github.javaparser.ast.type.Type t, PackageResolver packageResolver) {
        return typeReferenceConverter.convert(t, packageResolver);
    }

    private List<ConstructorDeclaration> collectConstructorsFrom(TypeDeclaration typeDeclaration) {
        return new ConstructorsCollector().collect(typeDeclaration);
    }

    private List<MethodDeclaration> collectMethodsFrom(TypeDeclaration typeDeclaration) {
        return new MethodsCollector().collect(typeDeclaration);
    }

    private List<FieldDeclaration> collectFieldsFrom(TypeDeclaration typeDeclaration) {
        return new FieldsCollector().collect(typeDeclaration);
    }

    private void addBasicData(Type type, CompilationUnit compilationUnit, TypeDeclaration typeDeclaration) {
        type.setPackageName(compilationUnit.getPackage().getPackageName());
        type.setName(typeDeclaration.getName());
        type.setType(getTypeType(typeDeclaration));
    }

    private String getTypeType(TypeDeclaration typeDeclaration) {
        if (typeDeclaration instanceof EnumDeclaration) {
            return "enum";
        }
        ClassOrInterfaceDeclaration coid = (ClassOrInterfaceDeclaration) typeDeclaration;
        return coid.isInterface() ? "interface" : "class";
    }

    private CompilationUnit getCompilationUnitFrom(File file) {
        try {
             return JavaParser.parse(file);
        } catch (Exception e) {
            System.out.println(e);
            throw new ParseException();
        }
    }
}
