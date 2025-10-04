package com.borec.backend.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.borec.backend.entity.Zprava;
import com.borec.backend.repository.ZpravaRepository;

import jakarta.annotation.PostConstruct;

@Service
@Transactional
public class ZpravaService {

    @Autowired
    private ZpravaRepository zpravaRepository;
	private List<Zprava> listForClientApplication = List.of();
	private ListForClientApplicationLoader listLoader = new ListForClientApplicationLoader();

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
    	return this.listForClientApplication ;
    }
    
    @PostConstruct
    private void postConstruct() {
    	listLoader.start();
    }
    
    @Transactional
    class ListForClientApplicationLoader extends Thread {
    	
    	public void run() {
    		while(true) {
    			load();
    			sleep();
    		}
    	}

        @Transactional(readOnly = true, isolation = Isolation.REPEATABLE_READ)
		private void load() {
	        List<Zprava> list =  zpravaRepository.listForClientApplication(new Date());
			if(!list.equals(listForClientApplication)) {
				listForClientApplication = list;
				System.out.println("listForClientApplication has been replaced.");
			}
		}
    }
 
}
