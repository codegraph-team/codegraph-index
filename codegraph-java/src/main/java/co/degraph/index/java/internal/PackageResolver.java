package co.degraph.index.java.internal;

import com.github.javaparser.ast.type.Type;

public interface PackageResolver {
    String resolve(Type t, String typeName);
}
