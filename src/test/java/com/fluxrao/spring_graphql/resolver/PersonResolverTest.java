package com.fluxrao.spring_graphql.resolver;

import com.fluxrao.spring_graphql.Entity.PersonEntity;
import com.fluxrao.spring_graphql.Repository.PersonRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest()
class PersonResolverTest {

    @Autowired
    private PersonResolver personResolver;

    @MockBean
    private PersonRepository personRepository;

    @MockBean
    private HttpServletRequest request;

    @Test
    void testCreatePersonWithHeader() {

        PersonInput input = new PersonInput();
        input.setFirstName("Flux");
        input.setLastName("Rao");
        input.setEmail("fluxrao@gmail.com");

        PersonEntity savedPerson = new PersonEntity();
        savedPerson.setId(1L);
        savedPerson.setFirstName("Flux");
        savedPerson.setLastName("Rao");
        savedPerson.setEmail("fluxrao@gmail.com");

        when(personRepository.save(any(PersonEntity.class))).thenReturn(savedPerson);
        when(request.getHeader("FID")).thenReturn("spartans");
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        PersonEntity result = personResolver.createPerson(input);
        Assertions.assertEquals("Flux", result.getFirstName());
        Assertions.assertEquals("Rao", result.getLastName());
        Assertions.assertEquals("fluxrao@gmail.com", result.getEmail());
    }

}