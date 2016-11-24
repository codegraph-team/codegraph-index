package com.dnfeitosa.codegraph.index.java.internal.javaparser;

import com.github.javaparser.ast.body.*;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.util.ArrayList;
import java.util.List;

public class FieldsCollector {

    public List<FieldDeclaration> collect(TypeDeclaration typeDeclaration) {
        List<FieldDeclaration> fields = new ArrayList<>();
        VoidVisitorAdapter visitor = new VoidVisitorAdapter<List<FieldDeclaration>>() {
            @Override
            public void visit(FieldDeclaration n, List<FieldDeclaration> arg) {
                arg.add(n);
            }
        };
        if (typeDeclaration instanceof ClassOrInterfaceDeclaration) {
            visitor.visit((ClassOrInterfaceDeclaration) typeDeclaration, fields);
        }
        if (typeDeclaration instanceof EnumDeclaration) {
            visitor.visit((EnumDeclaration) typeDeclaration, fields);
        }
        if (typeDeclaration instanceof AnnotationDeclaration) {
            visitor.visit((AnnotationDeclaration) typeDeclaration, fields);
        }
        if (typeDeclaration instanceof EmptyTypeDeclaration) {
            visitor.visit((EmptyTypeDeclaration) typeDeclaration, fields);
        }
        return fields;
    }
}
