package com.example.demo.clienttest;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest.BodyPublishers;
import java.nio.file.Paths;
import java.time.Duration;

public class RESTTestThread extends Thread {

    private static final HttpClient httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_1_1)
            .connectTimeout(Duration.ofSeconds(10))
            .build();
	static final int ATTEMPT_COUNT = 10;
	private String fileName;
	private double timeSum = 0.0;
	
	public RESTTestThread(String threadName, String fileName) {
		super(threadName);
		this.fileName = fileName;
	}
	
	public double getTimeSum() {
		return timeSum ;
	}

	@Override
	public void run() {
		for (int i = 0; i < ATTEMPT_COUNT; i++) {
			try {
				updateWatchWithID2(i + 1);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				System.out.println(this.getName());
				e.printStackTrace();
			}
		}
	}

	private void updateWatchWithID2(int i) throws URISyntaxException, IOException, InterruptedException {
 		HttpRequest request = HttpRequest.newBuilder()
 				.uri(URI.create("http://localhost:8080/updatewatch/2"))
 		        .header("Content-Type", "application/json")
 				.PUT(BodyPublishers.ofFile(Paths.get(
 						getClass().getResource(this.fileName).toURI())))
 				.build();
 		
 		timeSum += processResponseReturnTime(request, i);
   	}
	
    private double processResponseReturnTime(HttpRequest request, int i) throws IOException, InterruptedException {
		long start = System.nanoTime();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

		long end = System.nanoTime();
		double time = (end - start) / 1000000.0;
		//System.out.println("Time: " + time  + " ms");

		// print response headers
        HttpHeaders headers = response.headers();
        headers.map().forEach((k, v) -> System.out.println(k + ":" + v));

        int responseCode = response.statusCode();
		// print status code
        System.out.println("Thread : " + getName() + ", attempt : " + i);
        System.out.println("Response Code : " + responseCode );

        // print response body
        System.out.println(response.body());			
		

		return time;
	}

	
}
