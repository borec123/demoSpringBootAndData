package com.borec.backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.borec.backend.entity.Zprava;
import com.borec.backend.repository.ZpravaRepository;

@Service
@Transactional
public class ZpravaService {

    @Autowired
    private ZpravaRepository zpravaRepository;

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public Zprava insert(Zprava zprava) {
        return zpravaRepository.save(zprava);
    }

    @Transactional(readOnly = true, isolation = Isolation.REPEATABLE_READ)
    public List<Zprava> list() {

        List<Zprava> list =  zpravaRepository.findAll();

        return list;
    }
 
    @Transactional(readOnly = true, isolation = Isolation.REPEATABLE_READ)
    public List<Zprava> listForClientApplication() {

        List<Zprava> list =  zpravaRepository.listForClientApplication();

        return list;
    }
 
}
