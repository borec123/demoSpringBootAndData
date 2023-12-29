package com.borec.backend.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.borec.backend.entity.Person;

@Repository
public interface PersonRepository extends CustomJpaRepository<Person, Long>  {

    @Query("SELECT count(a) FROM Person a, Person b WHERE a.firstName <> ?1") //" WHERE a.first_name like $1 and b.first_name like $1")
    Long findByFirstNameCrossJoin(String name);

}
