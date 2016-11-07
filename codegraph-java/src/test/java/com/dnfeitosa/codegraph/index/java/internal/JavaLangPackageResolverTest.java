package com.dnfeitosa.codegraph.index.java.internal;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

public class JavaLangPackageResolverTest {

    @Test
    public void shouldResolveThePackageNameForJavaLangClasses() {
        JavaLangPackageResolver resolver = new JavaLangPackageResolver();

        assertThat(resolver.resolve(null, "Long"), is("java.lang"));
        assertThat(resolver.resolve(null, "String"), is("java.lang"));
        assertThat(resolver.resolve(null, "Thread"), is("java.lang"));
        assertNull(resolver.resolve(null, "ArrayList"));
        assertNull(resolver.resolve(null, "List"));
    }
}
