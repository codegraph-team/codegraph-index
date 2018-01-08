package co.degraph.ant.converters;

import co.degraph.client.resources.Dependency;
import org.apache.ivy.core.module.id.ModuleId;
import org.apache.ivy.core.module.id.ModuleRevisionId;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ModuleRevisionConverterTest {

    private ModuleRevisionConverter converter;

    @Before
    public void setUp() {
        converter = new ModuleRevisionConverter();
    }

    @Test
    public void shouldConvertAModuleRevisionToDependency() {
        ModuleRevisionId module = new ModuleRevisionId(new ModuleId("co.degraph", "codegraph-ant"), "1.0");

        Dependency artifact = converter.toDependency(module);

        assertThat(artifact.getName(), is("codegraph-ant"));
        assertThat(artifact.getOrganization(), is("co.degraph"));
        assertThat(artifact.getVersion(), is("1.0"));
    }
}
