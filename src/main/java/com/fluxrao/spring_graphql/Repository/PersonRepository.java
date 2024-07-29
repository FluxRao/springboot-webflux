package com.fluxrao.spring_graphql.Repository;

import com.fluxrao.spring_graphql.Entity.PersonEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<PersonEntity, Long> {
}
