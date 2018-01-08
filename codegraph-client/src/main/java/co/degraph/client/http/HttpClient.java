package co.degraph.client.http;

import co.degraph.client.CodegraphClient;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import java.net.URI;
import java.net.URISyntaxException;


public class HttpClient {

    private static final Log LOGGER = LogFactory.getLog(CodegraphClient.class);
    private final String baseUrl;

    public HttpClient(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public Response post(String path, Object body, ContentType contentType) {
        try {
            org.apache.http.client.HttpClient client = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(getUri(path));
            LOGGER.debug(String.format("Client request: %s", httpPost));
            httpPost.setEntity(createEntity(body, contentType));
            return new Response(client.execute(httpPost));
        } catch (Exception e) {
            throw new HttpClientException(e);
        }
    }

    private URI getUri(String path) throws URISyntaxException {
        return URI.create(baseUrl + "/" + path);
    }

    private StringEntity createEntity(Object body, ContentType contentType) {
        String serialized = contentType.getSerializer().serialize(body);
        return new StringEntity(serialized, contentType.getType());
    }
}
