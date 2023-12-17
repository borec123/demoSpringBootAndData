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

    @Test
    void testInsertWatch() throws IOException, InterruptedException, URISyntaxException {


        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8082/insertwatch"))
                .header("Content-Type", "application/json")
                .PUT(BodyPublishers.ofFile(Paths.get(
                        Objects.requireNonNull(getClass().getResource("person.json")).toURI())))
                .build();

        int responseCode = processResponse(request);

        assertEquals(HttpStatus.CREATED.value(), responseCode);

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


}
