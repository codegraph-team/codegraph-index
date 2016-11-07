package com.dnfeitosa.codegraph.index.java.internal.javaparser;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.TypeDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.util.ArrayList;
import java.util.List;

public class FieldsCollector {

    public List<FieldDeclaration> collect(TypeDeclaration typeDeclaration) {
        List<FieldDeclaration> fields = new ArrayList<>();
        new VoidVisitorAdapter<List<FieldDeclaration>>() {
            @Override
            public void visit(FieldDeclaration n, List<FieldDeclaration> arg) {
                arg.add(n);
            }
        }.visit((ClassOrInterfaceDeclaration)typeDeclaration, fields);
        return fields;
    }
}
