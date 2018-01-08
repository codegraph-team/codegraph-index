package co.degraph.client;

import org.junit.Test;

public class ClientTest {

    @Test(expected = InvalidServerUrlException.class)
    public void shouldThrowExceptionWhenUrlIsNull() {
        new CodegraphClient(null);
    }

    @Test(expected = InvalidServerUrlException.class)
    public void shouldThrowExceptionWhenUrlIsEmpty() {
        new CodegraphClient("");
    }
}
