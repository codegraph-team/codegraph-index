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
            return convert(referenceType.getType(), packageResolver);
        }

        if (t instanceof ClassOrInterfaceType) {
            return toType(t, packageResolver, (ClassOrInterfaceType) t);
        }

        if (t instanceof PrimitiveType) {
            Type type = new Type();
            type.setName(((PrimitiveType) t).getType().name().toLowerCase());
            return type;
        }

        throw new UnknownTypeException("Don't know how to handle " + t.getClass());
    }

    private Type toType(com.github.javaparser.ast.type.Type t, PackageResolver packageResolver, ClassOrInterfaceType coit) {
        Type type = new Type();
        type.setName(coit.getName());
        type.setPackageName(packageResolver.resolve(t, coit.getName()));
        return type;
    }
}
