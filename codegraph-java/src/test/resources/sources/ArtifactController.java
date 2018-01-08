package co.degraph.server.api.controllers;

import co.degraph.core.models.Artifact;
import co.degraph.server.api.converters.ArtifactResourceConverter;
import co.degraph.server.api.resources.ArtifactResource;
import co.degraph.server.api.services.ArtifactService;
import co.degraph.server.api.services.ItemDoesNotExistException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ArtifactController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ArtifactController.class);

    private final ArtifactService artifactService;
    private final ArtifactResourceConverter artifactResourceConverter;

    @Autowired
    public ArtifactController(ArtifactService artifactService, ArtifactResourceConverter artifactResourceConverter) {
        this.artifactService = artifactService;
        this.artifactResourceConverter = artifactResourceConverter;
    }

    @RequestMapping(value = "/artifacts/{id}")
    public ResponseEntity<ArtifactResource> getArtifact(@PathVariable("id") Long id) {
        try {
            Artifact artifact = artifactService.loadArtifact(id);

            return new ResponseEntity<>(artifactResourceConverter.toResource(artifact), HttpStatus.OK);
        } catch (ItemDoesNotExistException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
