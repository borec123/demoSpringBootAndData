package com.backend.reactive.clienttest;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.time.Duration;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RESTClientTest {

    private static final HttpClient httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_1_1)
            .connectTimeout(Duration.ofSeconds(10))
            .build();

    @Test
    void testInsertWatch() throws IOException, InterruptedException {

        String json = "{\"firstName\": \"Leoš\",\"lastName\": \"Mareš\", \"avatar\": \"https://gravatar.com/avatar/99df1a5b2917db695be7ad69e46d9164?s=400&d=robohash&r=x\"}";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8082/insertwatch"))
                .header("Content-Type", "application/json")
                .PUT(BodyPublishers.ofString(json))
                .build();

        int responseCode = processResponse(request);

        assertEquals(HttpStatus.CREATED.value(), responseCode);

    }

    @Test
    void testInsert50Watches() throws IOException, InterruptedException {

        for (int i = 0; i < 500; i++) {
            testInsertWatch();
        }

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

    private static final int THREAD_COUNT = 4;

    @Test
    public void testMassThread() throws InterruptedException {

        double timeSum = 0.0;

        RESTTestThread[] threads = new RESTTestThread[THREAD_COUNT];


        threads[0] = new RESTTestThread("t" + 0);
        threads[1] = new RESTTestThread("t" + 1);
        threads[2] = new RESTTestThread("t" + 2);
        threads[3] = new RESTTestThread("t" + 3);

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
