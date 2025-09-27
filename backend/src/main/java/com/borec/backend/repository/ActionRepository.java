package com.borec.backend.repository;

import org.springframework.stereotype.Repository;

import com.borec.backend.entity.Action;



@Repository
public interface ActionRepository extends CustomJpaRepository<Action, Long>  {


}

