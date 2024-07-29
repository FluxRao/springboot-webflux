package com.fluxrao.spring_graphql.resolver;

import com.fluxrao.spring_graphql.Entity.PersonEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.graphql.test.tester.HttpGraphQlTester;
import org.springframework.test.context.ContextConfiguration;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@ContextConfiguration(classes = GraphQlTestConfig.class)
class PersonResolverIntegrationTest {

    @Autowired
    private HttpGraphQlTester httpGraphQlTester;
/*
    @MockBean
    private PersonRepository personRepository;*/

    @Test
    void testListPeople() {
        String query = """
                 query MyQuery {
                   listPeople {
                     email
                     firstName
                     id
                     lastName
                   }
                 }
                """;

        httpGraphQlTester.document(query).execute().path("listPeople").entityList(PersonEntity.class).hasSizeGreaterThan(2);
    }

    @Test
    void testGetPerson() {
        String mutation = """
                    query getPerson($id: ID!){
                                    getPerson(id: $id) {
                                        id
                                        firstName
                                        lastName
                                        email
                                    }
                                }
                """;

        httpGraphQlTester.document(mutation).variable("id", 1).execute().path("getPerson").entity(PersonEntity.class).satisfies(person -> {
            assertNotNull(person);
            assertEquals(1L, person.getId());
            assertEquals("Yash", person.getFirstName());
            assertEquals("Rao", person.getLastName());
            assertEquals("yashrao@gmail.com", person.getEmail());
        });
    }

    @Test
    void testCreatePersonMutationWithHeaders() {

        String createPersonMutation = """
                mutation createPerson($input: PersonInput!) {
                  createPerson(input: $input) {
                    email
                    firstName
                    id
                    lastName
                  }
                }
                """;

        PersonInput personInput = new PersonInput();
        personInput.setEmail("shubhmane@gmail.com");
        personInput.setFirstName("Shubh");
        personInput.setLastName("Mane");

        PersonEntity personEntity = new PersonEntity();
        personEntity.setFirstName("Shubhankar");
        personEntity.setLastName("Mane");
        personEntity.setId(5L);
        personEntity.setEmail("shubhmane@gmail.com");

        /*when(personRepository.save(any())).thenReturn(Optional.of(personEntity));*/

        httpGraphQlTester.document(createPersonMutation).variable("input", personInput).execute().path("createPerson").entity(PersonEntity.class).satisfies(person -> {
            assertEquals("shubhmane@gmail.com", person.getEmail());
        });
    }
}