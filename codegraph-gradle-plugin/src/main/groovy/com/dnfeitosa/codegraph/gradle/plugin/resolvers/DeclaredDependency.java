package com.dnfeitosa.codegraph.gradle.plugin.resolvers;

import org.gradle.api.artifacts.Dependency;

import java.util.HashSet;
import java.util.Set;

public class DeclaredDependency {
    private final Dependency dependency;
    private final Set<String> configurations = new HashSet<String>();

    public DeclaredDependency(Dependency dependency) {
        this.dependency = dependency;
    }

    public void addConfiguration(String configuration) {
        configurations.add(configuration);
    }

    public String getName() {
        return dependency.getName();
    }

    public String getGroup() {
        return dependency.getGroup();
    }

    public String getVersion() {
        return dependency.getVersion();
    }

    public Set<String> getConfigurations() {
        return configurations;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DeclaredDependency that = (DeclaredDependency) o;

        return dependency.equals(that.dependency);
    }

    @Override
    public int hashCode() {
        return dependency.hashCode();
    }
}
