package com.dnfeitosa.codegraph.gradle.plugin.converters

import org.gradle.api.internal.artifacts.DefaultModuleVersionIdentifier
import org.gradle.api.internal.artifacts.DefaultResolvedDependency
import org.junit.Before
import org.junit.Test

import static org.hamcrest.CoreMatchers.hasItem
import static org.hamcrest.CoreMatchers.is
import static org.junit.Assert.assertThat

class ResolvedDependencyConverterTest {
    private ResolvedDependencyConverter converter

    @Before
    void setUp() {
        converter = new ResolvedDependencyConverter()
    }

    @Test
    void shouldConvertAResolvedDependencyToDependency() {
        def resolvedDependency = new DefaultResolvedDependency(new DefaultModuleVersionIdentifier("organization", "name", "version"), "compile")

        def dependency = converter.toDependency(resolvedDependency)

        assertThat(dependency.name, is('name'))
        assertThat(dependency.organization, is('organization'))
        assertThat(dependency.version, is('version'))
//        assertThat(dependency.type, is('jar'))
//        assertThat(dependency.extension, is('jar'))
        assertThat(dependency.configurations, hasItem("compile"))
    }
}
