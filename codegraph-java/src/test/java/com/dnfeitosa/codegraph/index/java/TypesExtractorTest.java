package com.dnfeitosa.codegraph.index.java;

import com.dnfeitosa.codegraph.client.resources.Field;
import com.dnfeitosa.codegraph.client.resources.Method;
import com.dnfeitosa.codegraph.client.resources.Parameter;
import com.dnfeitosa.codegraph.client.resources.Type;
import com.dnfeitosa.codegraph.index.java.internal.JavaLangPackageResolver;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.List;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class TypesExtractorTest {

    private TypesExtractor typesExtractor;

    @Before
    public void setUp() {
        typesExtractor = new TypesExtractor();
    }

    @Test
    public void shouldParseAJavaFileAndReturnAType() {
        File sourceFile = new File(getClass().getResource("/sources/ArtifactController.java").getFile());

        List<Type> types = typesExtractor.parseTypes(sourceFile, new JavaLangPackageResolver());
        Type type = types.get(0);

        assertThat(type.getName(), is("ArtifactController"));
        assertThat(type.getType(), is("class"));
        assertThat(type.getPackageName(), is("com.dnfeitosa.codegraph.server.api.controllers"));

        List<Method> methods = type.getMethods().stream()
                .sorted(comparing(Method::getName))
                .collect(toList());

        assertThat(methods.size(), is(2));

        {
            Method constructor = methods.get(0);
            assertThat(constructor.getName(), is("ArtifactController"));

            List<Parameter> parameters = constructor.getParameters();
            assertThat(parameters.size(), is(2));

            assertThat(parameters.get(0).getType().getName(), is("ArtifactService"));
            assertThat(parameters.get(0).getType().getPackageName(), is("com.dnfeitosa.codegraph.server.api.services"));
            assertThat(parameters.get(1).getType().getName(), is("ArtifactResourceConverter"));
            assertThat(parameters.get(1).getType().getPackageName(), is("com.dnfeitosa.codegraph.server.api.converters"));

            assertTrue(constructor.getReturnTypes().isEmpty());
        }{
            Method getArtifact = methods.get(1);
            assertThat(getArtifact.getName(), is("getArtifact"));

            List<Parameter> parameters = getArtifact.getParameters();
            assertThat(parameters.size(), is(1));

            assertThat(parameters.get(0).getType().getName(), is("Long"));
            assertThat(parameters.get(0).getType().getPackageName(), is("java.lang"));

            List<Type> returnTypes = getArtifact.getReturnTypes();
            assertThat(returnTypes.size(), is(1));
            assertThat(returnTypes.get(0).getName(), is("ResponseEntity"));
            assertThat(returnTypes.get(0).getPackageName(), is("org.springframework.http"));
        }

        List<Field> fields = type.getFields().stream()
                .sorted(comparing(Field::getName))
                .collect(toList());

        assertThat(fields.size(), is(3));

        {
            Field field = fields.get(0);
            assertThat(field.getName(), is("LOGGER"));
            assertThat(field.getType().getName(), is("Logger"));
        }{
            Field field = fields.get(1);
            assertThat(field.getName(), is("artifactResourceConverter"));
            assertThat(field.getType().getName(), is("ArtifactResourceConverter"));
        }{
            Field field = fields.get(2);
            assertThat(field.getName(), is("artifactService"));
            assertThat(field.getType().getName(), is("ArtifactService"));
        }
    }
}
