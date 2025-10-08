package com.borec.backend.service;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Timer;
import java.util.TimerTask;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.borec.backend.entity.Zprava;
import com.borec.backend.entity.ZpravaArchive;
import com.borec.backend.entity.ZpravaArchiveCreated;
import com.borec.backend.pojo.ZpravyResponse;
import com.borec.backend.repository.ZpravaArchiveRepository;
import com.borec.backend.repository.ZpravaRepository;

import jakarta.annotation.PostConstruct;

@Service
@Transactional
public class ZpravaService {

	@Autowired
	private ZpravaRepository zpravaRepository;
	
	@Autowired
	private ZpravaArchiveRepository zpravaArchiveRepository;
	
	private ZpravyResponse listForClientApplication = new ZpravyResponse(List.of());
	private Timer timer;


	@Transactional(isolation = Isolation.REPEATABLE_READ)
	public Zprava insert(Zprava zprava) {
		Zprava z = zpravaRepository.save(zprava);
		Thread.ofVirtual().start(() -> removeSchedulerAndLoadAndScheduleNextLoad());
		return z;
	}

	@Transactional(isolation = Isolation.REPEATABLE_READ)
	public void delete(Zprava zprava) {
		ZpravaArchive zpravaArchive = new ZpravaArchive(zprava, ZpravaArchiveCreated.USER);
		zpravaRepository.delete(zprava);
		zpravaArchiveRepository.save(zpravaArchive);
		
		Thread.ofVirtual().start(() -> removeSchedulerAndLoadAndScheduleNextLoad());
	}

	@Transactional(readOnly = true, isolation = Isolation.REPEATABLE_READ)
	public List<Zprava> list() {
		List<Zprava> list = zpravaRepository.findAll();
		return list;
	}

	@Transactional(readOnly = true, isolation = Isolation.REPEATABLE_READ)
	public ZpravyResponse listForClientApplication() {
		return this.listForClientApplication;
	}

	@PostConstruct
	private void postConstruct() {
		// listLoader.start();
		loadAndScheduleNextLoad();
	}

	/*
	@Transactional
	class ListForClientApplicationLoader extends Thread {

		public void run() {
			while (true) {
				load();
				try {
					sleep(10000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	} */

		/*
		 * @Transactional(readOnly = true, isolation = Isolation.REPEATABLE_READ)
		 * private void load() { ZpravyResponse list = new
		 * ZpravyResponse(zpravaRepository.listForClientApplication(new Date()));
		 * //if(!list.equals(listForClientApplication)) { listForClientApplication =
		 * list; //System.out.println("listForClientApplication has been replaced.");
		 * //} }
		 */

	private void loadAndScheduleNextLoad() {
		load();
		scheduleNextLoad();
	}

	private void removeSchedulerAndLoadAndScheduleNextLoad() {
		System.out.println("Forced reload.");
		if (timer != null) {
			timer.cancel();
		}
		loadAndScheduleNextLoad();
	}

	@Transactional(readOnly = true, isolation = Isolation.REPEATABLE_READ)
	private void load() {
		System.out.println("Load launched.");
		ZpravyResponse list = new ZpravyResponse(zpravaRepository.listForClientApplication(new Date()));
		listForClientApplication = list;
	}

	private void scheduleNextLoad() {

		// --- schedule only if size() > 0
		if (listForClientApplication.getList().size() > 0) {
			Zprava minByCas_do = listForClientApplication.getList().stream()
					.min(Comparator.comparing(Zprava::getCas_do)).orElseThrow(NoSuchElementException::new);

			// --- Schedule a Task Once:
			final TimerTask task = new TimerTask() {
				public void run() {
					loadAndScheduleNextLoad();
				}
			};
			timer = new Timer(); // "Timer"
			long delay = minByCas_do.getCas_do().getTime() - System.currentTimeMillis();
			timer.schedule(task, delay);
			System.out.println("Next load is scheduled at: " + minByCas_do.getCas_do());
		}
	}

}
