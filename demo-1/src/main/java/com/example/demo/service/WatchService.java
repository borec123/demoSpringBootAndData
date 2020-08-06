package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.hibernate.engine.jdbc.BlobProxy;
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
	public List<Watch> list() {

		List<Watch> list = new ArrayList<>();
		watchRepository.findAll().forEach(e -> {
			list.add(new Watch().setPrice(e.getPrice())
					.setId(e.getId())
					.setTitle(e.getTitle())
					.setDescription(e.getDescription())
					.setFountain(e.getFountain()));
		});
		return list;
	}

	@Transactional
	public WatchEntity update(Watch newWatch, Long id) {

		Optional<WatchEntity> e = watchRepository.findById(id);

		if (e.isPresent()) {
			WatchEntity watch = e.get();
			watch.setTitle(newWatch.getTitle());
			watch.setDescription(newWatch.getDescription());
			watch.setPrice(newWatch.getPrice());
			watch.setFountain(newWatch.getFountain());
			// watch.setFountain(BlobProxy.generateProxy(newWatch.getFountain().getBytes()));
			return watchRepository.save(watch);
			
		} else {
			throw new EntityNotFoundException(String.format("Watch entity with id:%d does not exists.", id));
		}
	}

	@Transactional
	public WatchEntity insert(Watch newWatch) {

		return watchRepository.save(new WatchEntity()
  	        		.setTitle(newWatch.getTitle())
  	        		.setPrice(newWatch.getPrice())
  	        		.setDescription(newWatch.getDescription())
  	  	    	  	.setFountain(newWatch.getFountain())
  	        		//.setFountain(BlobProxy.generateProxy(newWatch.getFountain().getBytes()))
        		);
	}

	public void createMockData() {
		watchRepository.save(new WatchEntity().setTitle("Mock watch 1"));
		watchRepository.save(new WatchEntity().setTitle("Mock watch 2"));
		watchRepository.flush();
	}


}
