package com.dnfeitosa.codegraph.db.repositories;

import java.util.Iterator;
import org.springframework.data.neo4j.repository.GraphRepository;

public interface RepositoryIterator extends GraphRepository, Iterator {

}