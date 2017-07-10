package com.dnfeitosa.codegraph.gradle.plugin.resolvers;

import org.gradle.api.Project;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.artifacts.Dependency;
import org.gradle.api.internal.DefaultDomainObjectSet;
import org.gradle.api.internal.artifacts.DefaultDependencySet;
import org.gradle.api.internal.artifacts.dependencies.DefaultExternalModuleDependency;
import org.gradle.testfixtures.ProjectBuilder;
import org.junit.Before;
import org.junit.Test;

import java.util.Set;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
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

        Configuration compile = configuration("compile",
            dependency("org.apache.commons", "commons-lang3", "3.4"),
            dependency("com.dnfeitosa.codegraph", "codegraph-core", "1.0")
        );
        Configuration testCompile = configuration("testCompile",
            dependency("com.dnfeitosa.codegraph", "codegraph-core", "1.0"),
            dependency("junit", "junit", "4.12")
        );

        project.getConfigurations().add(compile);
        project.getConfigurations().add(testCompile);

        Set<DeclaredDependency> declaredDependencies = dependenciesResolver.getDeclaredDependenciesOf(project);
        assertThat(declaredDependencies.size(), is(3));

        assertThat(findIn(declaredDependencies, "org.apache.commons", "commons-lang3", "3.4").getConfigurations(), hasItems("compile"));
        assertThat(findIn(declaredDependencies, "com.dnfeitosa.codegraph", "codegraph-core", "1.0").getConfigurations(), hasItems("compile", "testCompile"));
        assertThat(findIn(declaredDependencies, "junit", "junit", "4.12").getConfigurations(), hasItems("testCompile"));
    }

    private DeclaredDependency findIn(Set<DeclaredDependency> declaredDependencies, String group, String name, String version) {
        for (DeclaredDependency declaredDependency : declaredDependencies) {
            if (group.equals(declaredDependency.getGroup())
                && name.equals(declaredDependency.getName())
                && version.equals(declaredDependency.getVersion())) {
                return declaredDependency;
            }
        }
        return null;
    }

    private Dependency dependency(String group, String name, String version) {
        return new DefaultExternalModuleDependency(group, name, version);
     }

    private Configuration configuration(String name, Dependency... dependencies) {
        Configuration mock = mock(Configuration.class);
        when(mock.getName()).thenReturn(name);

        DefaultDependencySet dependencySet = new DefaultDependencySet(name, new DefaultDomainObjectSet<Dependency>(Dependency.class, asList(dependencies)));
        when(mock.getDependencies()).thenReturn(dependencySet);
        return mock;
    }
}
