package com.borec.backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.borec.backend.entity.Action;
import com.borec.backend.entity.Person;
import com.borec.backend.repository.ActionRepository;

@Service
//@Transactional(isolation = Isolation.REPEATABLE_READ)
public class ActionService {
	
    @Autowired
    private ActionRepository actionRepository;

    //@Transactional(isolation = Isolation.REPEATABLE_READ)
    public Action insert(Action action) {
    	Action a = actionRepository.save(action);
        return a;
    }
    
    //@Transactional(readOnly = true, isolation = Isolation.REPEATABLE_READ)
    public List<Action> list() {

        List<Action> list =  actionRepository.findAll();
        return list;
    }
    
}
