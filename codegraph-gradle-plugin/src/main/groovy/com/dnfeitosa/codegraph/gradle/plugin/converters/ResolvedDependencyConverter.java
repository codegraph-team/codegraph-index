package com.dnfeitosa.codegraph.gradle.plugin.converters;

import com.dnfeitosa.codegraph.client.resources.Dependency;
import com.dnfeitosa.codegraph.gradle.plugin.resolvers.DeclaredDependency;

import java.util.Set;

public class ResolvedDependencyConverter {

    public Dependency toDependency(DeclaredDependency declaredDependency) {
        String group = declaredDependency.getGroup();
        String name = declaredDependency.getName();
        String version = declaredDependency.getVersion();
        Set<String> configurations = declaredDependency.getConfigurations();
        return new Dependency(group, name, version, configurations);
    }
}
