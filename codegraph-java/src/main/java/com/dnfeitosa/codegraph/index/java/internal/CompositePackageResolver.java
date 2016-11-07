package com.dnfeitosa.codegraph.index.java.internal;

import com.github.javaparser.ast.type.Type;

public class CompositePackageResolver implements PackageResolver {

    private final DefaultPackageResolver defaultPackageResolver;
    private final JavaLangPackageResolver javaLangPackageResolver;

    public CompositePackageResolver(DefaultPackageResolver defaultPackageResolver, JavaLangPackageResolver javaLangPackageResolver) {
        this.defaultPackageResolver = defaultPackageResolver;
        this.javaLangPackageResolver = javaLangPackageResolver;
    }

    public void add(String typeName, String packageName) {
        defaultPackageResolver.add(typeName, packageName);
    }

    @Override
    public String resolve(Type t, String typeName) {
        String resolved = javaLangPackageResolver.resolve(t, typeName);
        if (resolved != null) {
            return resolved;
        }
        return defaultPackageResolver.resolve(t, typeName);
    }

}
