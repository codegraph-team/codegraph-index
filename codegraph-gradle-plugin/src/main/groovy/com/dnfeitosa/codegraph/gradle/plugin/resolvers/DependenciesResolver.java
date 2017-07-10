package com.dnfeitosa.codegraph.gradle.plugin.resolvers;

import org.gradle.api.Project;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.artifacts.ConfigurationContainer;
import org.gradle.api.artifacts.Dependency;
import org.gradle.api.artifacts.DependencySet;
import org.gradle.api.logging.Logger;
import org.gradle.api.logging.Logging;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class DependenciesResolver {

    private static final Logger logger = Logging.getLogger(DependenciesResolver.class);


    public Set<DeclaredDependency> getDeclaredDependenciesOf(Project project) {
        Map<Dependency, DeclaredDependency> declaredDependencies = new HashMap<Dependency, DeclaredDependency>();
        ConfigurationContainer configurations = project.getConfigurations();
        for (Configuration configuration : configurations) {
            DependencySet dependencies = configuration.getDependencies();
            logger.info("Configuration '{}' has {} dependencies declared.", configuration.getName(), dependencies.size());
            for (Dependency dependency : dependencies) {
                if (!declaredDependencies.containsKey(dependency)) {
                    declaredDependencies.put(dependency, new DeclaredDependency(dependency));
                }
                declaredDependencies.get(dependency).addConfiguration(configuration.getName());
            }
        }
        return new HashSet<DeclaredDependency>(declaredDependencies.values());
    }

}
