package com.dnfeitosa.codegraph.gradle.plugin;

import com.dnfeitosa.codegraph.client.CodegraphClient;
import com.dnfeitosa.codegraph.client.resources.Artifact;
import com.dnfeitosa.codegraph.client.resources.Dependency;
import com.dnfeitosa.codegraph.client.resources.Type;
import com.dnfeitosa.codegraph.gradle.plugin.converters.ProjectConverter;
import com.dnfeitosa.codegraph.gradle.plugin.converters.ResolvedDependencyConverter;
import com.dnfeitosa.codegraph.gradle.plugin.resolvers.DeclaredDependency;
import com.dnfeitosa.codegraph.gradle.plugin.resolvers.DependenciesResolver;
import com.dnfeitosa.codegraph.index.java.TypesExtractor;
import com.dnfeitosa.codegraph.index.java.internal.JavaLangPackageResolver;
import com.dnfeitosa.codegraph.index.java.internal.PackageResolver;
import org.gradle.api.Project;
import org.gradle.api.file.FileTree;
import org.gradle.api.logging.Logger;
import org.gradle.api.logging.Logging;
import org.gradle.api.plugins.JavaPluginConvention;
import org.gradle.api.tasks.SourceSet;
import org.gradle.api.tasks.SourceSetContainer;

import java.io.File;
import java.util.List;
import java.util.Set;

public class CodegraphIndexer {

    private static final Logger LOGGER = Logging.getLogger(CodegraphIndexer.class);

    private final ProjectConverter projectConverter;
    private final ResolvedDependencyConverter dependencyConverter;
    private final DependenciesResolver dependencyResolver;
    private final CodegraphClient client;

    public CodegraphIndexer(CodegraphClient client) {
        this(client, new DependenciesResolver(), new ProjectConverter(), new ResolvedDependencyConverter(), new TypesExtractor());
    }

    CodegraphIndexer(CodegraphClient client, DependenciesResolver dependencyResolver, ProjectConverter projectConverter,
                     ResolvedDependencyConverter dependencyConverter, TypesExtractor typesExtractor) {
        this.client = client;
        this.dependencyResolver = dependencyResolver;
        this.projectConverter = projectConverter;
        this.dependencyConverter = dependencyConverter;
    }

    public void index(Project project) {
        Artifact artifact = projectConverter.toArtifact(project);

        addDependencies(artifact, project);
//        addTypes(artifact, project);
        client.addArtifact(artifact);
    }

    private void addTypes(Artifact artifact, Project project) {
        TypesExtractor typesExtractor = new TypesExtractor();
        PackageResolver packageResolver = new JavaLangPackageResolver();

        LOGGER.lifecycle("-----------------");
        JavaPluginConvention pluginConvention = project.getConvention().getPlugin(JavaPluginConvention.class);
        SourceSetContainer sourceSets = pluginConvention.getSourceSets();
        LOGGER.info("Found {} source sets: {}", sourceSets.size(), sourceSets);
        for (SourceSet sourceSet : sourceSets) {
            LOGGER.info("Collecting types from '{}'", sourceSet.getName());
            FileTree files = sourceSet.getAllJava().getAsFileTree();
            for (File file : files) {
                List<Type> types = typesExtractor.parseTypes(file, packageResolver);
                LOGGER.lifecycle("File {} contains {} types", file, types.size());
                for (Type type : types) {
                    type.setUsage(sourceSet.getName());
                    artifact.addType(type);
                }
            }
        }

        LOGGER.lifecycle("-----------------");
    }

    private void addDependencies(Artifact artifact, Project project) {
        Set<DeclaredDependency> resolvedDependencies = resolveDependencies(project);
        for (DeclaredDependency declaredDependency : resolvedDependencies) {
            Dependency dependency = dependencyConverter.toDependency(declaredDependency);
            artifact.addDependency(dependency);
        }
    }

    private Set<DeclaredDependency> resolveDependencies(Project project) {
        return dependencyResolver.getDeclaredDependenciesOf(project);
    }
}
