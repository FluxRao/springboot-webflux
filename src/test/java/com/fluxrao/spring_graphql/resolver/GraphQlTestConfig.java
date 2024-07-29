package com.fluxrao.spring_graphql.resolver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.graphql.test.tester.HttpGraphQlTester;
import org.springframework.test.web.reactive.server.WebTestClient;

@Configuration
public class GraphQlTestConfig {

    @Autowired
    private ApplicationContext context;

    @Bean
    public WebTestClient.Builder webTestClientBuilder() {
        return WebTestClient.bindToServer().baseUrl("http://localhost:8080/graphql");
    }

    @Bean
    public HttpGraphQlTester graphQlTester(WebTestClient.Builder builder) {

        WebTestClient webTestClient = builder
                .defaultHeader("FID", "spartans")
                .build();

        return HttpGraphQlTester.create(webTestClient);
    }
}
