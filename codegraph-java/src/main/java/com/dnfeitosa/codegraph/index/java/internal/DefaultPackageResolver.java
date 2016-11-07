package com.dnfeitosa.codegraph.index.java.internal;

import com.github.javaparser.ast.type.Type;

import java.util.HashMap;
import java.util.Map;

public class DefaultPackageResolver implements PackageResolver {

    private final Map<String, String> packages = new HashMap<>();

    public String resolve(Type t, String typeName) {
        return packages.get(typeName);
    }

    public void add(String typeName, String packageName) {
        packages.put(typeName, packageName);
    }
}
