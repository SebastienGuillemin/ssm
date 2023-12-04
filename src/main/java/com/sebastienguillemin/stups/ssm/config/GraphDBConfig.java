package com.sebastienguillemin.stups.ssm.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ontotext.graphdb.repository.http.GraphDBHTTPRepository;
import com.ontotext.graphdb.repository.http.GraphDBHTTPRepositoryBuilder;

@Configuration
public class GraphDBConfig {
    @Bean
    public GraphDBHTTPRepository graphDBHTTPRepository() {
        GraphDBHTTPRepository repository = new GraphDBHTTPRepositoryBuilder()
                .withRepositoryUrl("http://127.0.0.1:7200/repositories/STUPS_NoSameAs")
                .build();
        
        return repository;
    }
}
