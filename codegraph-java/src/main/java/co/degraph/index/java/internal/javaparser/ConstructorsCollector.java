package co.degraph.index.java.internal.javaparser;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.body.EnumDeclaration;
import com.github.javaparser.ast.body.TypeDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.util.ArrayList;
import java.util.List;

public class ConstructorsCollector {

    public List<ConstructorDeclaration> collect(TypeDeclaration typeDeclaration) {
        List<ConstructorDeclaration> constructors = new ArrayList<>();
        VoidVisitorAdapter visitor = new VoidVisitorAdapter<List<ConstructorDeclaration>>() {
            @Override
            public void visit(ConstructorDeclaration n, List<ConstructorDeclaration> arg) {
                arg.add(n);
            }
        };
        if (typeDeclaration instanceof ClassOrInterfaceDeclaration) {
            visitor.visit((ClassOrInterfaceDeclaration) typeDeclaration, constructors);
        }
        if (typeDeclaration instanceof EnumDeclaration) {
            visitor.visit((EnumDeclaration) typeDeclaration, constructors);
        }
        return constructors;
    }
}
