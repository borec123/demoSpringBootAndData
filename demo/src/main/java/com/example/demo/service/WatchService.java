package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import com.example.demo.entity.WatchEntity;
import com.example.demo.pojo.Watch;
import com.example.demo.repository.WatchRepository;

@Service
@Transactional
public class WatchService {

	@Autowired
	private WatchRepository watchRepository;

	@Transactional(readOnly = true)
	public List<WatchEntity> list() {
		
		return watchRepository.findAll();
	}
	
	@Transactional
	public WatchEntity update(Watch newWatch, Long id) {
		
	    return watchRepository.findById(id)
	  	      .map(watch -> {
	  	    	  watch.setTitle(newWatch.getTitle());
	  	    	  watch.setDescription(newWatch.getDescription());
	  	    	  watch.setPrice(newWatch.getPrice());
	  	    	  watch.setFountain(newWatch.getFountain());
	  	        return watchRepository.save(watch);
	  	      })
	  	      .orElseGet(() -> {
	  	        return watchRepository.save(new WatchEntity()
	  	        		.setId(id)
	  	        		.setTitle(newWatch.getTitle())
	  	        		.setPrice(newWatch.getPrice())
	  	        		.setDescription(newWatch.getDescription())
	  	        		.setFountain(newWatch.getFountain())
	  	        		);
	  	      });
	}

	public void createMockData() {
		  watchRepository.save(new WatchEntity().setTitle("Mock watch 1") ); 
		  watchRepository.save(new WatchEntity().setTitle("Mock watch 2") ); 
		  watchRepository.flush();
	}

	public Object findById(Long id) {
		
		return watchRepository.findById(id);
	}
	
	
}
