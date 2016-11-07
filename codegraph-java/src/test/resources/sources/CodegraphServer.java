package com.dnfeitosa.codegraph.server.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;

@EnableAutoConfiguration
@ComponentScan("com.dnfeitosa.codegraph.*")
@ImportResource({ "classpath:/codegraph-db.xml" })
public class CodegraphServer {

    public static void main(String[] args) {
        SpringApplication.run(CodegraphServer.class, args);
    }
}