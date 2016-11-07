package com.dnfeitosa.codegraph.index.java.internal;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.expr.QualifiedNameExpr;
import com.github.javaparser.ast.type.Type;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CompilationUnitPackageResolver implements PackageResolver {

    private final Map<String, String> packages = new HashMap<>();
    private final PackageResolver basePackageResolver;

    public CompilationUnitPackageResolver(CompilationUnit compilationUnit, PackageResolver basePackageResolver) {
        this.basePackageResolver = basePackageResolver;
        addPackagesFrom(compilationUnit);
    }

    public void add(String typeName, String packageName) {
        packages.put(typeName, packageName);
    }

    @Override
    public String resolve(Type t, String typeName) {
        String packageName = packages.get(typeName);
        if (packageName != null) {
            return packageName;
        }
        return basePackageResolver.resolve(t, typeName);
    }

    public Map<String, String> getResolvedPackages() {
        return new HashMap<>(packages);
    }

    private void addPackagesFrom(CompilationUnit compilationUnit) {
        List<ImportDeclaration> imports = extractImportsFrom(compilationUnit);
        imports.stream().map(i -> (QualifiedNameExpr)i.getName()).forEach(_import -> {
            String typeName = _import.getName();
            add(typeName, getPackageName(_import, typeName));
        });
    }

    private String getPackageName(QualifiedNameExpr _import, String typeName) {
        String fullName = _import.toString();
        return fullName.substring(0, fullName.lastIndexOf(typeName) - 1);
    }

    private List<ImportDeclaration> extractImportsFrom(CompilationUnit compilationUnit) {
        List<ImportDeclaration> imports = new ArrayList<>();
        new VoidVisitorAdapter<List<ImportDeclaration>>() {
            @Override
            public void visit(ImportDeclaration n, List<ImportDeclaration> arg) {
                arg.add(n);
            }
        }.visit(compilationUnit, imports);
        return imports;
    }
}
