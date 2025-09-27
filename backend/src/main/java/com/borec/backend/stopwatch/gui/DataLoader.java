package com.borec.backend.stopwatch.gui;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.List;

import com.borec.backend.entity.Zprava;
import com.borec.backend.pojo.ZpravyResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DataLoader {

	private static final HttpClient httpClient = HttpClient.newBuilder().version(HttpClient.Version.HTTP_1_1)
			.connectTimeout(Duration.ofSeconds(10)).build();
	static final String HOST_ = "localhost";
	static final String PORT_ = "8194";

	static List<Zprava> loadDtata() throws IOException, InterruptedException {
		HttpRequest request = HttpRequest.newBuilder().GET()
				.uri(URI.create("http://" + HOST_ + ":" + PORT_ + "/listForClientApplication"))
				.setHeader("User-Agent", "Java 11 HttpClient Bot") // add request header
				.build();

		HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

		String body = response.body();

		ObjectMapper om = new ObjectMapper();
		ZpravyResponse zpravyResponse = om.readValue(body, ZpravyResponse.class);

		return zpravyResponse.getList();
	}

}
