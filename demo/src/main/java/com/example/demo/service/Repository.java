package com.example.demo.service;


import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.example.demo.model.BaseEntity;



/**
 * Base extendable interface for all repositories.
 */
@NoRepositoryBean
public interface Repository<T extends BaseEntity<ID>, ID extends Serializable> 
		extends /* CustomJpaRepository<T, ID>, */ JpaRepository<T, ID> {

}
