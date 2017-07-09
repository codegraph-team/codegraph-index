package com.dnfeitosa.codegraph.gradle.plugin.converters

import com.dnfeitosa.codegraph.client.resources.Dependency
import org.gradle.api.artifacts.ResolvedDependency

class ResolvedDependencyConverter {

    Dependency toDependency(ResolvedDependency resolvedDependency) {
        def configurations = new HashSet()
        configurations.add(resolvedDependency.configuration)

        def dependency = new Dependency(resolvedDependency.moduleGroup, resolvedDependency.moduleName, resolvedDependency.moduleVersion, configurations)
//        dependency.type = 'jar'
//        dependency.extension = 'jar'
        return dependency
    }
}
