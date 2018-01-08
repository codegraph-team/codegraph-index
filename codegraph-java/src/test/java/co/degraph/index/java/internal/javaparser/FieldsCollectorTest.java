package co.degraph.index.java.internal.javaparser;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.FieldDeclaration;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class FieldsCollectorTest {

    @Test
    public void shouldCollectAllFieldsFromATypeDeclaration() throws Exception {
        CompilationUnit compilationUnit = JavaParser.parse(getClass().getResourceAsStream("/sources/ArtifactController.java"));

        List<FieldDeclaration> methodDeclarations = new FieldsCollector().collect(compilationUnit.getTypes().get(0));

        assertThat(methodDeclarations.size(), is(3));
    }
}
