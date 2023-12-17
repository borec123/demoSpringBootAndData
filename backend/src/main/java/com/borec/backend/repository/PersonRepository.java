package com.borec.backend.repository;

import org.springframework.stereotype.Repository;

import com.borec.backend.entity.Person;

@Repository
public interface PersonRepository extends CustomJpaRepository<Person, Long>  {

}
