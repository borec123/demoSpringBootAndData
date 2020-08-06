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
 * This is a client test which is testing a running REST endpoint using HttpClient.
 * Excluded from Maven build. 
 * (Note that this is NOT a typical JUnit test.) 
 */
class RESTClientTest {

    private static final HttpClient httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_1_1)
            .connectTimeout(Duration.ofSeconds(10))
            .build();
    
    /**
     * Inserts a watch containing data from file.json.
     * It is doing exactly the same as following command:
     * 
     * curl -v -X PUT localhost:8080/insertwatch -H 'Content-type:application/json' -d 
     * '{"title": "Prim","price": "250000", "description": "A watch with a water fountain picture",
     * "fountain":"R0lGODlhAQABAIAAAAUEBAAAACwAAAAAAQABAAACAkQBADs="}'
     * 
     * @throws IOException
     * @throws InterruptedException
     * @throws URISyntaxException
     */
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
	
    /**
     *  Inserts a watch containing data from bigfile.json.
     *  
     * It is doing exactly the same as following command:
     * 
     * curl -v -X PUT localhost:8080/insertwatch -H 'Content-type:application/json' -d 
     * '{"title": "Prim","price": "250000", "description": "A watch with a VERY BIG fountain picture",
     * "fountain":"R0lGODlhAQABAIAAAAUEBAAAACwAAAAAAQABAAACAkQBADsR0lGODlhAQABAIAAAAUEBAAAACwAAAAAAQABAAACAkQBADsR0lGODlhAQABAIAAAAUEBAAAACwAAAAAAQABAAACAkQBADs...
     * 
     * @throws IOException
     * @throws InterruptedException
     * @throws URISyntaxException
     */
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
	
    /**
     * Tests a wrong input.
     * Inserts a watch containing data from wrongfile.json.
     * 
     * It is doing exactly the same as following command:
     * 
     * curl -v -X PUT localhost:8080/insertwatch -H 'Content-type:application/json' -d 
     * '{"title": "Prim","price": <b>"klm"</b>, "description": "A watch with a water fountain picture",
     * "fountain":"R0lGODlhAQABAIAAAAUEBAAAACwAAAAAAQABAAACAkQBADs="}'
     * 
     * @throws IOException
     * @throws InterruptedException
     * @throws URISyntaxException
     */
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

    /**
     * Returns all {@link com.example.demo.entity.WatchEntity} entities.
     */
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
    

    /**
     * Updates a watch of id '2' with data containing in file.json.
     * 
     * @throws IOException
     * @throws InterruptedException
     * @throws URISyntaxException
     */
    @Test
 	void testUpdateWatch() throws IOException, InterruptedException, URISyntaxException {
 		
         
 		HttpRequest request = HttpRequest.newBuilder()
 				.uri(URI.create("http://localhost:8080/updatewatch/2"))
 		        .header("Content-Type", "application/json")
 				.PUT(BodyPublishers.ofFile(Paths.get(
 						getClass().getResource("file.json").toURI())))
 				.build();
 		
         int responseCode = processResponse(request);
         
 		assertEquals(HttpStatus.OK.value(), responseCode);
 		
 	}
 	
    
    /**
     * Updates a watch of non-existing id 999.
     * 
     * @throws IOException
     * @throws InterruptedException
     * @throws URISyntaxException
     */
    @Test
 	void testUpdateWatchNotFound() throws IOException, InterruptedException, URISyntaxException {
 		
         
 		HttpRequest request = HttpRequest.newBuilder()
 				.uri(URI.create("http://localhost:8080/updatewatch/999"))
 		        .header("Content-Type", "application/json")
 				.PUT(BodyPublishers.ofFile(Paths.get(
 						getClass().getResource("file.json").toURI())))
 				.build();
 		
         int responseCode = processResponse(request);
         
 		assertEquals(HttpStatus.NOT_FOUND.value(), responseCode);
 		
 	}
 	
    @Test
	void testInsertWatchXML() throws IOException, InterruptedException, URISyntaxException {
		
        
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create("http://localhost:8080/insertwatch"))
		        .header("Content-Type", "application/xml")
				.PUT(BodyPublishers.ofFile(Paths.get(
						getClass().getResource("file.xml").toURI())))
				.build();
		
        int responseCode = processResponse(request);
        
		assertEquals(HttpStatus.CREATED.value(), responseCode);
		
	}
	

    

}
