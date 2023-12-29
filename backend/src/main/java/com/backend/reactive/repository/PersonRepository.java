package com.backend.reactive.repository;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

import com.backend.reactive.entity.Person;

import reactor.core.publisher.Mono;

@Repository
public interface PersonRepository  extends R2dbcRepository<Person, Long> {

    @Query("SELECT count(a.*) FROM person a, person b WHERE a.first_name <> $1") //" WHERE a.first_name like $1 and b.first_name like $1")
    Mono<Long> findByFirstNameCrossJoin(String name);

}
