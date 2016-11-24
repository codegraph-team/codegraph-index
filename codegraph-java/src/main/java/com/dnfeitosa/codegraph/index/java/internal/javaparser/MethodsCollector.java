package com.dnfeitosa.codegraph.index.java.internal.javaparser;

import com.github.javaparser.ast.body.*;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.util.ArrayList;
import java.util.List;

public class MethodsCollector {

    public List<MethodDeclaration> collect(TypeDeclaration typeDeclaration) {
        List<MethodDeclaration> methodDeclarations = new ArrayList<>();
        VoidVisitorAdapter visitor = new VoidVisitorAdapter<List<MethodDeclaration>>() {
            @Override
            public void visit(MethodDeclaration n, List<MethodDeclaration> arg) {
                arg.add(n);
            }
        };
        if (typeDeclaration instanceof ClassOrInterfaceDeclaration) {
            visitor.visit((ClassOrInterfaceDeclaration) typeDeclaration, methodDeclarations);
        }
        if (typeDeclaration instanceof EnumDeclaration) {
            visitor.visit((EnumDeclaration) typeDeclaration, methodDeclarations);
        }
        if (typeDeclaration instanceof AnnotationDeclaration) {
            visitor.visit((AnnotationDeclaration) typeDeclaration, methodDeclarations);
        }
        if (typeDeclaration instanceof EmptyTypeDeclaration) {
            visitor.visit((EmptyTypeDeclaration) typeDeclaration, methodDeclarations);
        }
        return methodDeclarations;
    }
}
