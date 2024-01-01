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
import java.util.Objects;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

        int responseCode = processResponse(request);
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

        int responseCode = processResponse(request);
        assertEquals(HttpStatus.CREATED.value(), responseCode);
    }

    @Test
    void testInsert500Watches() throws IOException, InterruptedException, URISyntaxException {

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
