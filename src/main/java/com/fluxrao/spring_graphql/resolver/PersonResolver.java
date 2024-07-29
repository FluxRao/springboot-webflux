package com.fluxrao.spring_graphql.resolver;

import com.fluxrao.spring_graphql.Entity.PersonEntity;
import com.fluxrao.spring_graphql.Repository.PersonRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.List;

@Controller
public class PersonResolver {

    @Autowired
    private PersonRepository personRepository;

    private HttpServletRequest getCurrentRequest() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return attributes != null ? attributes.getRequest() : null;
    }

    @QueryMapping
    public PersonEntity getPerson(@Argument Long id) {
        return personRepository.findById(id).orElse(null);
    }

    @QueryMapping
    public List<PersonEntity> listPeople() {
        return personRepository.findAll();
    }

    @MutationMapping
    public PersonEntity createPerson(@Argument PersonInput input) {

        HttpServletRequest request = getCurrentRequest();
        String fID = request != null ? request.getHeader("FID") : null;

        if ("spartans".equalsIgnoreCase(fID)) {
            PersonEntity personEntity = new PersonEntity();
            personEntity.setFirstName(input.getFirstName());
            personEntity.setLastName(input.getLastName());
            personEntity.setEmail(input.getEmail());
            return personRepository.save(personEntity);
        }
        return null;
    }

    @MutationMapping
    public PersonEntity updatePerson(@Argument Long id, @Argument PersonInput input) {
        PersonEntity personEntity = personRepository.findById(id).orElse(null);
        if (personEntity != null) {
            personEntity.setFirstName(input.getFirstName());
            personEntity.setLastName(input.getLastName());
            personEntity.setEmail(input.getEmail());

            return personRepository.save(personEntity);
        }
        return null;
    }
}
