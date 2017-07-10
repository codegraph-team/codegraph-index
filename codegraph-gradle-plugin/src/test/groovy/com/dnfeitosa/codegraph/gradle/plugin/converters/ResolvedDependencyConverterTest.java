package com.dnfeitosa.codegraph.gradle.plugin.converters;

import com.dnfeitosa.codegraph.gradle.plugin.resolvers.DeclaredDependency;
import org.gradle.api.internal.artifacts.dependencies.DefaultExternalModuleDependency;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ResolvedDependencyConverterTest {

    private ResolvedDependencyConverter converter;

    @Before
    public void setUp() {
        converter = new ResolvedDependencyConverter();
    }

    @Test
    public void shouldConvertAResolvedDependencyToDependency() {
        DeclaredDependency declaredDependency = new DeclaredDependency(new DefaultExternalModuleDependency("organization", "name", "version"));
        declaredDependency.addConfiguration("compile");
        declaredDependency.addConfiguration("testCompile");

        com.dnfeitosa.codegraph.client.resources.Dependency dependency = converter.toDependency(declaredDependency);

        assertThat(dependency.getName(), is("name"));
        assertThat(dependency.getOrganization(), is("organization"));
        assertThat(dependency.getVersion(), is("version"));
        assertThat(dependency.getConfigurations(), hasItems("compile", "testCompile"));
    }
}
