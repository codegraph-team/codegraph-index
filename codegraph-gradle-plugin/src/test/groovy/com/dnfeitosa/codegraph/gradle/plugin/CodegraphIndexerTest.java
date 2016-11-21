package com.dnfeitosa.codegraph.gradle.plugin;

import com.dnfeitosa.codegraph.client.CodegraphClient;
import com.dnfeitosa.codegraph.client.resources.Artifact;
import com.dnfeitosa.codegraph.gradle.plugin.converters.ProjectConverter;
import com.dnfeitosa.codegraph.gradle.plugin.converters.ResolvedDependencyConverter;
import com.dnfeitosa.codegraph.gradle.plugin.resolvers.DependenciesResolver;
import com.dnfeitosa.codegraph.index.java.TypesExtractor;
import org.gradle.api.Project;
import org.gradle.api.artifacts.ResolvedDependency;
import org.gradle.api.internal.artifacts.DefaultModuleVersionIdentifier;
import org.gradle.api.internal.artifacts.DefaultResolvedDependency;
import org.gradle.testfixtures.ProjectBuilder;
import org.junit.Before;

import static com.dnfeitosa.codegraph.gradle.plugin.utils.ArrayUtils.asSet;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CodegraphIndexerTest {
    private DependenciesResolver dependenciesResolver;
    private ProjectConverter projectConverter;
    private ResolvedDependencyConverter dependencyConverter;
    private CodegraphIndexer task;
    private Project project;
    private CodegraphClient client;

    @Before
    public void setUp() {
        client = mock(CodegraphClient.class);
        dependenciesResolver = mock(DependenciesResolver.class);
        projectConverter = mock(ProjectConverter.class);
        dependencyConverter = mock(ResolvedDependencyConverter.class);

        project = ProjectBuilder.builder().build();

        task = new CodegraphIndexer(client, dependenciesResolver, projectConverter, dependencyConverter, new TypesExtractor());
    }

//    @Test
    public void shouldExecuteTheTask() {
        Artifact projectArtifact = new Artifact();
        when(projectConverter.toArtifact(project)).thenReturn(projectArtifact);
        ResolvedDependency dependency1 = dependency("dependency1", "1.0");
        ResolvedDependency dependency2 = dependency("dependency2", "0.9");
        when(dependenciesResolver.resolveDependencies(project)).thenReturn(asSet(dependency1, dependency2));

        task.index(project);

        verify(dependencyConverter).toArtifact(dependency1);
        verify(dependencyConverter).toArtifact(dependency2);
        verify(client).addArtifact(projectArtifact);
    }

    private ResolvedDependency dependency(String name, String version) {
        return new DefaultResolvedDependency(new DefaultModuleVersionIdentifier("the-group", name, version), "configuration");
    }

}