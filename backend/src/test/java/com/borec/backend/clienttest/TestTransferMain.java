package com.borec.backend.clienttest;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;



public class TestTransferMain {
    
    private static final HttpClient httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_1_1)
            .connectTimeout(Duration.ofSeconds(10))
            .build();
    
    static final String HOST_ = "localhost";
    static final String PORT_ = "8080";  
    
    public static void main(String[] args) throws IOException, InterruptedException, URISyntaxException {

		HttpRequest request2 = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("http://" + HOST_ + ":" + PORT_ + "/transfer"))
                .header("Content-Type", "application/json")
                .build();

        HttpResponse<String> response2 = httpClient.send(request2, HttpResponse.BodyHandlers.ofString());
        
        
        System.out.println("Status code: " + response2.statusCode());
    }
}
