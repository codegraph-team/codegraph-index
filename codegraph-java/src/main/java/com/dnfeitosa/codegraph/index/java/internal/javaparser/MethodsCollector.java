package com.dnfeitosa.codegraph.index.java.internal.javaparser;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.EnumDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.TypeDeclaration;
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
        return methodDeclarations;
    }
}
