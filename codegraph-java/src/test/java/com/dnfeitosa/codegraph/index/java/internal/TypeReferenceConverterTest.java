package com.dnfeitosa.codegraph.index.java.internal;

import com.dnfeitosa.codegraph.client.resources.Type;
import com.dnfeitosa.codegraph.index.java.UnknownTypeException;
import com.github.javaparser.Position;
import com.github.javaparser.Range;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.ast.type.PrimitiveType;
import com.github.javaparser.ast.type.ReferenceType;
import com.github.javaparser.ast.type.VoidType;
import com.github.javaparser.ast.visitor.GenericVisitor;
import com.github.javaparser.ast.visitor.VoidVisitor;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

public class TypeReferenceConverterTest {

    private TypeReferenceConverter converter;
    private DefaultPackageResolver packageResolver;

    @Before
    public void setUp() {
        converter = new TypeReferenceConverter();
        packageResolver = new DefaultPackageResolver();
        packageResolver.add("Long", "java.lang");
        packageResolver.add("Map", "java.util");
    }

    @Test
    public void shouldConvertAClassOrInterfaceTypeReferenceToType() throws Exception {
        ClassOrInterfaceType classType = new ClassOrInterfaceType("Long");
        ReferenceType referenceType = new ReferenceType(classType);

        Type type = converter.convert(referenceType, packageResolver);

        assertThat(type.getName(), is("Long"));
        assertThat(type.getPackageName(), is("java.lang"));
    }

    @Test
    @Ignore("Need to determine whether generic types will be returned as multiple types or with a composite name. E.g. Map<java.lang.String, java.lang.Long>")
    public void shouldConvertAGenericClassOrInterfaceTypeReferenceToType() {
        ClassOrInterfaceType classType = new ClassOrInterfaceType("Map");
        classType.setTypeArgs(asList(new ReferenceType(new ClassOrInterfaceType("String")), new ReferenceType(new ClassOrInterfaceType("Long"))));
        ReferenceType referenceType = new ReferenceType(classType);

        System.out.println(referenceType);

        Type type = converter.convert(referenceType, packageResolver);

        assertThat(type.getName(), is("Map"));
        assertThat(type.getPackageName(), is("java.util"));
    }

    @Test
    public void shouldConvertAPrimitiveTypeReferenceToType() {
        PrimitiveType primitiveType = new PrimitiveType(PrimitiveType.Primitive.Int);

        Type type = converter.convert(primitiveType, packageResolver);

        assertThat(type.getName(), is("int"));
    }

    @Test
    public void shouldConvertAVoidTypeReferenceToType() {
        Type type = converter.convert(new VoidType(new Range(Position.UNKNOWN, Position.UNKNOWN)), new DefaultPackageResolver());

        assertThat(type.getName(), is("void"));
        assertNull(type.getPackageName());
    }

    @Test(expected = UnknownTypeException.class)
    public void shouldThrowExceptionIfTypeIsNotRecognized() {
        converter.convert(new UnknownType(), packageResolver);
    }

    static class UnknownType extends com.github.javaparser.ast.type.Type {
        @Override
        public <R, A> R accept(GenericVisitor<R, A> v, A arg) {
            return null;
        }

        @Override
        public <A> void accept(VoidVisitor<A> v, A arg) {
        }
    }
}
