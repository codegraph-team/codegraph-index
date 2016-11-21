package com.dnfeitosa.codegraph.db.repositories;

import org.springframework.data.neo4j.repository.GraphRepository;

@Repository
public class TypeRepository extends BaseRepository implements GraphRepository {

    @Autowired
    private BaseTypeRepository baseRepository;
}