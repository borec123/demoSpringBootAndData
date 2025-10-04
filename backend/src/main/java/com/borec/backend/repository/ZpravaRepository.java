package com.borec.backend.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.borec.backend.entity.Zprava;



@Repository
public interface ZpravaRepository extends CustomJpaRepository<Zprava, Long>  {
    @Query("select a FROM Zprava a WHERE (a.cas_od <= ?1) and (a.cas_do > ?1) and (a.zapnuto = TRUE) ORDER BY cas_od desc")   //?1
    List<Zprava> listForClientApplication(Date date);
}
