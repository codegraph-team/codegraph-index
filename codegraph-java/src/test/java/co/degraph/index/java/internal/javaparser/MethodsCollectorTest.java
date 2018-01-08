package co.degraph.index.java.internal.javaparser;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class MethodsCollectorTest {

    @Test
    public void shouldCollectAllMethodsFromClassType() throws Exception {
        CompilationUnit compilationUnit = JavaParser.parse(getClass().getResourceAsStream("/sources/ArtifactController.java"));

        List<MethodDeclaration> methodDeclarations = new MethodsCollector().collect(compilationUnit.getTypes().get(0));

        assertThat(methodDeclarations.size(), is(1));
    }
}
