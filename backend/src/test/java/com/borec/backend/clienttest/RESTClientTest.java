package com.borec.backend.clienttest;

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
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import com.borec.backend.entity.Person;
import com.borec.backend.pojo.PersonResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class RESTClientTest {

    private static final HttpClient httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_1_1)
            .connectTimeout(Duration.ofSeconds(10))
            .build();
    static final String HOST_ = "localhost";
    static final String PORT_ = "8080";

    @Test
    void testList() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("http://" + HOST_ + ":" + PORT_ + "/list"))
                .setHeader("User-Agent", "Java 11 HttpClient Bot") // add request header
                .build();

        int responseCode = processResponse(request).getKey();
        assertEquals(HttpStatus.OK.value(), responseCode);
    }

    @Test
    void testInsertWatch() throws IOException, InterruptedException, URISyntaxException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://" + HOST_ + ":" + PORT_ + "/insertwatch"))
                .header("Content-Type", "application/json")
                .PUT(BodyPublishers.ofFile(Paths.get(
                        Objects.requireNonNull(getClass().getResource("person.json")).toURI())))
                .build();

        int responseCode = processResponsePerson(request).getKey();
        assertEquals(HttpStatus.CREATED.value(), responseCode);
    }

    /**
     * Tests update of the first entity in the list
     * @throws IOException
     * @throws InterruptedException
     * @throws URISyntaxException
     */
    @Test
    void testUpdate() throws IOException, InterruptedException, URISyntaxException {

    	
    	HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("http://" + HOST_ + ":" + PORT_ + "/list"))
                .setHeader("User-Agent", "Java 11 HttpClient Bot") // add request header
                .build();

        PersonResponse personResponse = processResponse(request).getValue();
        
        List<Person> list = personResponse.getList();
        
        Person first = list.get(0);
        
        first.setScore(0d);
        
        ObjectMapper om = new ObjectMapper();
        
        request = HttpRequest.newBuilder()
                .uri(URI.create("http://" + HOST_ + ":" + PORT_ + "/insertwatch"))
                .header("Content-Type", "application/json")
                .PUT(BodyPublishers.ofString(om.writeValueAsString(first)))
                .build();

        int responseCode = processResponsePerson(request).getKey();
        assertEquals(HttpStatus.CREATED.value(), responseCode);
    }
        
    @Test
    void testTransfer() throws IOException, InterruptedException, URISyntaxException {

    	/*
    	HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("http://" + HOST_ + ":" + PORT_ + "/list"))
                .setHeader("User-Agent", "Java 11 HttpClient Bot") // add request header
                .build();

        PersonResponse personResponse = processResponse(request).getValue();
        
        List<Person> list = personResponse.getList();
        
        Person first = list.get(0);
        Person second = list.get(1);
        
        if(first == null || second == null) {
        	System.out.println(" NULL !!!");
        	fail();
        }
        
        
        ObjectMapper om = new ObjectMapper();

*/
    	
        //String str = om.writeValueAsString(first) + om.writeValueAsString(second) + om.writeValueAsString(new Double(10.0));
        
		HttpRequest request2 = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("http://" + HOST_ + ":" + PORT_ + "/transfer"))
                .header("Content-Type", "application/json")
                .build();

        HttpResponse<String> response2 = httpClient.send(request2, HttpResponse.BodyHandlers.ofString());
        
        
        assertEquals(HttpStatus.OK.value(), response2.statusCode());
    }

    @Test
    void testInsert500Watches() throws IOException, InterruptedException, URISyntaxException {

        for (int i = 0; i < 500; i++) {
            testInsertWatch();
        }

    }

    private Map.Entry<Integer, PersonResponse> processResponse(HttpRequest request) throws IOException, InterruptedException {
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
        String body = response.body();
        System.out.println(body);
        
        ObjectMapper om = new ObjectMapper();
        PersonResponse personResponse = om.readValue(body, PersonResponse.class);
        

        System.out.println("response size: " + personResponse.getSize());

        return Map.entry(responseCode, personResponse);
    }

    private Map.Entry<Integer, Person> processResponsePerson(HttpRequest request) throws IOException, InterruptedException {
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
        String body = response.body();
        System.out.println(body);
        
        ObjectMapper om = new ObjectMapper();
        Person person = om.readValue(body, Person.class);
        
        return Map.entry(responseCode, person);
    }

    private static final int THREAD_COUNT = 10;

    @Test
    public void testMassThread() throws InterruptedException {

        double timeSum = 0.0;

        RESTTestThread[] threads = new RESTTestThread[THREAD_COUNT];

        for (int i = 0; i < THREAD_COUNT; i++) {
            threads[i] = new RESTTestThread("t" + i);
        }

        for (int i = 0; i < THREAD_COUNT; i++) {
            threads[i].start();
        }
        for (int i = 0; i < THREAD_COUNT; i++) {
            threads[i].join();
        }
        for (int i = 0; i < THREAD_COUNT; i++) {
            timeSum += threads[i].getTimeSum();
        }

        System.out.println("Average response time (ms): " + timeSum / (THREAD_COUNT * RESTTestThread.ATTEMPT_COUNT));

    }
}
