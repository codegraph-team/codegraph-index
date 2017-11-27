package com.dnfeitosa.codegraph.client;

import com.dnfeitosa.codegraph.client.http.ContentType;
import com.dnfeitosa.codegraph.client.http.HttpClient;
import com.dnfeitosa.codegraph.client.resources.Artifact;
import com.dnfeitosa.codegraph.client.resources.Index;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Set;

import static java.lang.String.format;
import static org.apache.commons.lang.StringUtils.isBlank;

public class CodegraphClient {

    private static final Log LOGGER = LogFactory.getLog(CodegraphClient.class);

    private final HttpClient client;

    public CodegraphClient(String serverUrl) {
        LOGGER.info(format("Opening connection to Codegraph server at '%s'.", serverUrl));
        if (isBlank(serverUrl)) {
            throw new InvalidServerUrlException("Empty or invalid server URL provided.");
        }
        client = new HttpClient(serverUrl);
    }

    public void addArtifact(Artifact artifact, Set<Artifact> dependencyArtifacts) {
        client.post("/api/index", new Index(artifact, dependencyArtifacts), ContentType.JSON);
    }
}
