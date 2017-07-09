package com.dnfeitosa.codegraph.gradle.plugin.resolvers;

import org.gradle.api.Project;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.artifacts.ResolvedConfiguration;
import org.gradle.api.artifacts.ResolvedDependency;
import org.gradle.testfixtures.ProjectBuilder;
import org.junit.Before;
import org.junit.Test;

import static com.dnfeitosa.codegraph.gradle.plugin.utils.ArrayUtils.asSet;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DependenciesResolverTest {

    private DependenciesResolver dependenciesResolver;

    @Before
    public void setUp() {
        dependenciesResolver = new DependenciesResolver();
    }

    @Test
    public void shouldResolveTheDependenciesFromAllConfigurations() {
        Project project = ProjectBuilder.builder().build();

        Configuration compile = configuration("compile");
        Configuration testCompile = configuration("testCompile");

        project.getConfigurations().add(compile);
        project.getConfigurations().add(testCompile);

        dependenciesResolver.resolveDependencies(project);
    }

    private Configuration configuration(String name, ResolvedDependency... dependencies) {
        Configuration mock = mock(Configuration.class);
        when(mock.getName()).thenReturn(name);

        ResolvedConfiguration resolvedConfiguration = resolvedConfiguration(dependencies);
        when(mock.getResolvedConfiguration()).thenReturn(resolvedConfiguration);
        return mock;
    }

    private ResolvedConfiguration resolvedConfiguration(ResolvedDependency... dependencies) {
        ResolvedConfiguration resolvedConfiguration = mock(ResolvedConfiguration.class);
        when(resolvedConfiguration.getFirstLevelModuleDependencies()).thenReturn(asSet(dependencies));
        return resolvedConfiguration;
    }
}
