package com.borec.backend.repository;

import org.springframework.stereotype.Repository;

import com.borec.backend.entity.ZpravaArchive;

@Repository
public interface ZpravaArchiveRepository extends CustomJpaRepository<ZpravaArchive, Long>  {

}
