package com.dnfeitosa.codegraph.client;

import com.dnfeitosa.codegraph.client.http.ContentType;
import com.dnfeitosa.codegraph.client.http.HttpClient;
import com.dnfeitosa.codegraph.client.resources.Artifact;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import static java.lang.String.format;
import static org.apache.commons.lang.StringUtils.isBlank;

public class CodegraphClient {

    private static final Log LOGGER = LogFactory.getLog(CodegraphClient.class);
    private final HttpClient client;

    public CodegraphClient(String url) {
        LOGGER.info(format("Opening connection to Codegraph server at '%s'.", url));
        if (isBlank(url)) {
            throw new InvalidServerUrlException("Codegraph URL must be specified in configuration closure.");
        }
        client = new HttpClient(url);
    }

    public void addArtifact(Artifact artifact) {
        client.post("/api/index", new Index(artifact), ContentType.JSON);
    }

    public static class Index {
        private Artifact artifact;

        public Index(Artifact artifact) {
            this.artifact = artifact;
        }
    }
}
