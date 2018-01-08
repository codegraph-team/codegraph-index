package co.degraph.ant.converters;

import co.degraph.client.resources.Artifact;
import org.apache.tools.ant.Project;

public class ProjectConverter {

    public Artifact toArtifact(Project project) {
        Artifact artifact = new Artifact();
        artifact.setName(project.getProperty("ivy.module"));
        artifact.setOrganization(project.getProperty("ivy.organisation"));
        artifact.setVersion(project.getProperty("ivy.version"));
        artifact.setExtension("jar");
        artifact.setType("jar");
        return artifact;
    }
}
