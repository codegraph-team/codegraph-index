package com.dnfeitosa.codegraph.gradle.plugin.resolvers

import org.gradle.api.Project
import org.gradle.api.artifacts.ResolvedDependency
import org.gradle.api.logging.Logger
import org.gradle.api.logging.Logging

class DependenciesResolver {

    private static final Logger logger = Logging.getLogger(DependenciesResolver.class);

    Set<ResolvedDependency> resolveDependencies(Project project) {
        project.configurations.collect { conf ->
            def dependencies = conf.resolvedConfiguration.firstLevelModuleDependencies
            logger.info("Found {} dependencies for configuration {}.", dependencies.size(), conf.name)
            dependencies
        }.flatten()
    }
}
