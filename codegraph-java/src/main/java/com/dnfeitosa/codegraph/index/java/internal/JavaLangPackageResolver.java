package com.dnfeitosa.codegraph.index.java.internal;

import com.github.javaparser.ast.type.Type;

public class JavaLangPackageResolver implements PackageResolver {

    @Override
    public String resolve(Type t, String typeName) {
        try {
            Class.forName(String.format("java.lang.%s", typeName));
            return "java.lang";
        } catch (ClassNotFoundException e) {
            return null;
        }
    }
}
