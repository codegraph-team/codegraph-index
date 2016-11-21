package com.dnfeitosa.codegraph.index.java.internal;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.type.Type;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CompilationUnitPackageResolverTest {

    @Test
    public void shouldAddTheCompilationUnitsPackagesIntoThePackageResolverUponItsConstruction() throws Exception {
        CompilationUnit compilationUnit = JavaParser.parse(getClass().getResourceAsStream("/sources/ArtifactController.java"));

        CompilationUnitPackageResolver resolver = new CompilationUnitPackageResolver(compilationUnit, new DefaultPackageResolver());

        assertThat(resolver.getResolvedPackages().size(), is(13));
        System.out.println(resolver.getResolvedPackages());

        assertThat(resolver.resolve(null, "Artifact"), is("com.dnfeitosa.codegraph.core.models"));
        assertThat(resolver.resolve(null, "ArtifactResourceConverter"), is("com.dnfeitosa.codegraph.server.api.converters"));
        assertThat(resolver.resolve(null, "Logger"), is("org.slf4j"));
    }

    @Test
    public void shouldDelegateThePackageResolutionToTheDefaultResolverWhenCompilationUnitDoesNotContainAPackageForType() throws Exception {
        CompilationUnit compilationUnit = JavaParser.parse(getClass().getResourceAsStream("/sources/ArtifactController.java"));
        PackageResolver basePackageResolver = mock(PackageResolver.class);
        when(basePackageResolver.resolve(any(Type.class), any(String.class))).thenReturn("com.dnfeitosa.codegraph");

        CompilationUnitPackageResolver resolver = new CompilationUnitPackageResolver(compilationUnit, basePackageResolver);

        String packageName = resolver.resolve(null, "TypeNotPresentInCompilationUnit");
        assertThat(packageName, is("com.dnfeitosa.codegraph"));

        verify(basePackageResolver).resolve(null, "TypeNotPresentInCompilationUnit");
    }

    @Test
    public void shouldReturnTheCompilationUnitsPackageWhenAPackageCannotBeResolved() throws Exception {
        CompilationUnit compilationUnit = JavaParser.parse(getClass().getResourceAsStream("/sources/ArtifactController.java"));
        PackageResolver basePackageResolver = mock(PackageResolver.class);

        CompilationUnitPackageResolver resolver = new CompilationUnitPackageResolver(compilationUnit, basePackageResolver);

        String packageName = resolver.resolve(null, "TypeInSamePackageAsCompilationUnit");

        assertThat(packageName, is("com.dnfeitosa.codegraph.server.api.controllers"));
    }
}
