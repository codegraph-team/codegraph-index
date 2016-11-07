package com.dnfeitosa.codegraph.index.java.internal;

import com.dnfeitosa.codegraph.client.resources.Type;
import com.dnfeitosa.codegraph.index.java.UnknownTypeException;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.ast.type.PrimitiveType;
import com.github.javaparser.ast.type.ReferenceType;
import com.github.javaparser.ast.type.VoidType;

public class TypeReferenceConverter {

    public Type convert(com.github.javaparser.ast.type.Type t, PackageResolver packageResolver) {
        if (t instanceof VoidType) {
            Type type = new Type();
            type.setName("void");
            return type;
        }

        if (t instanceof ReferenceType) {
            ReferenceType referenceType = (ReferenceType) t;
            ClassOrInterfaceType coit = (ClassOrInterfaceType) referenceType.getType();

            Type type = new Type();
            type.setName(getName(coit));
            type.setPackageName(packageResolver.resolve(t, coit.getName()));
            return type;
        }

        if (t instanceof PrimitiveType) {
            Type type = new Type();
            type.setName(((PrimitiveType) t).getType().name().toLowerCase());
            return type;
        }

        throw new UnknownTypeException("Don't know how to handle " + t.getClass());
    }

    private String getName(ClassOrInterfaceType coit) {
        return coit.getName();
    }
}
