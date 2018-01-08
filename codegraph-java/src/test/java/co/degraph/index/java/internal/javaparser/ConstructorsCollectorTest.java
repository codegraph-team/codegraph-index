package co.degraph.index.java.internal.javaparser;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ConstructorDeclaration;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ConstructorsCollectorTest {

    @Test
    public void shouldCollectAndReturnAllConstructorFromClassDeclaration() throws Exception {
        CompilationUnit compilationUnit = JavaParser.parse(getClass().getResourceAsStream("/sources/ArtifactController.java"));

        List<ConstructorDeclaration> constructors = new ConstructorsCollector().collect(compilationUnit.getTypes().get(0));

        assertThat(constructors.size(), is(1));
    }
}
