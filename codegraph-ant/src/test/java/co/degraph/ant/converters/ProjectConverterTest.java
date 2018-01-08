package co.degraph.ant.converters;

import co.degraph.client.resources.Artifact;
import org.apache.tools.ant.Project;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ProjectConverterTest {

    private ProjectConverter converter;

    @Before
    public void setUp() {
        converter = new ProjectConverter();
    }

    @Test
    public void shouldConvertAProjectToArtifact() {
        String projectName = "ProjectName";
        String organization = "com.foo";
        String version = "1.0";

        Project project = new Project();

        project.setProperty("ivy.module", projectName);
        project.setProperty("ivy.organisation", organization);
        project.setProperty("ivy.version", version);

        Artifact artifact = converter.toArtifact(project);

        assertThat(artifact.getName(), is(projectName));
        assertThat(artifact.getOrganization(), is(organization));
        assertThat(artifact.getExtension(), is("jar"));
        assertThat(artifact.getType(), is("jar"));
        assertThat(artifact.getVersion(), is(version));
    }
}
