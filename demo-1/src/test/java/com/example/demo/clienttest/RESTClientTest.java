package com.example.demo.clienttest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.nio.file.Paths;
import java.time.Duration;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

/**
 * This is a client test testing a running REST endpoint using HttpClient.
 * Excluded from Maven build. (This is NOT a common JUnit test.) 
 */
class RESTClientTest {

    private static final HttpClient httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_1_1)
            .connectTimeout(Duration.ofSeconds(10))
            .build();
    
    //@Disabled
    @Test
	void testInsertWatch() throws IOException, InterruptedException, URISyntaxException {
		
        
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create("http://localhost:8080/insertwatch"))
		        .header("Content-Type", "application/json")
				.PUT(BodyPublishers.ofFile(Paths.get(
						getClass().getResource("file.json").toURI())))
				.build();
		
        int responseCode = processResponse(request);
        
		assertEquals(HttpStatus.CREATED.value(), responseCode);
		
	}
	
    @Test
	void testInsertWatchBigFile() throws IOException, InterruptedException, URISyntaxException {
		
        
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create("http://localhost:8080/insertwatch"))
		        .header("Content-Type", "application/json")
				.PUT(BodyPublishers.ofFile(Paths.get(
						getClass().getResource("bigfile.json").toURI())))
				.build();
		
        int responseCode = processResponse(request);
        
		assertEquals(HttpStatus.CREATED.value(), responseCode);
		
	}
	
    @Test
	void testInsertWatchWrongInput() throws IOException, InterruptedException, URISyntaxException {
		
        
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create("http://localhost:8080/insertwatch"))
		        .header("Content-Type", "application/json")
				.PUT(BodyPublishers.ofFile(Paths.get(
						getClass().getResource("wrongfile.json").toURI())))
				.build();
		
        int responseCode = processResponse(request);
        
		assertEquals(HttpStatus.BAD_REQUEST.value(), responseCode);
		
	}
	
    private int processResponse(HttpRequest request) throws IOException, InterruptedException {
		long start = System.nanoTime();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

		long end = System.nanoTime();
		System.out.println("Time: " + (end - start) / 1000000.0 + " ms");

		// print response headers
        HttpHeaders headers = response.headers();
        headers.map().forEach((k, v) -> System.out.println(k + ":" + v));

        int responseCode = response.statusCode();
		// print status code
        System.out.println("Response Code : " + responseCode );

        // print response body
        System.out.println(response.body());			
		

		return responseCode;
	}

	//@Disabled
    @Test
	void testList() {
		try {

			
			HttpRequest request = HttpRequest.newBuilder()
	                .GET()
	                .uri(URI.create("http://localhost:8080/list"))
	                .setHeader("User-Agent", "Java 11 HttpClient Bot") // add request header
	                .build();

	        int responseCode = processResponse(request);
	        

			assertEquals(HttpStatus.OK.value(), responseCode);
			

		} catch (Exception e) {
			e.printStackTrace();
			fail("Exception");
		}
	}

}
