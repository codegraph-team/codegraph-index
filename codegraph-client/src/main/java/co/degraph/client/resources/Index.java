package co.degraph.client.resources;

import java.util.Set;

public class Index {

    private final Artifact artifact;
    private final Set<Artifact> dependencyArtifacts;

    public Index(Artifact artifact, Set<Artifact> dependencyArtifacts) {
        this.artifact = artifact;
        this.dependencyArtifacts = dependencyArtifacts;
    }

    public Artifact getArtifact() {
        return artifact;
    }

    public Set<Artifact> getDependencyArtifacts() {
        return dependencyArtifacts;
    }
}
