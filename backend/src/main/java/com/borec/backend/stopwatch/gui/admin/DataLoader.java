package com.borec.backend.stopwatch.gui.admin;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
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
	private static Throwable throwable;

	static List<Zprava> sendLoadDataRequestAsync() throws IOException, InterruptedException {

		throwable = null;

		HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://" + HOST_ + ":" + PORT_ + "/listzprava"))
				.timeout(Duration.ofSeconds(10)).header("Content-Type", "application/json").GET().build();
		httpClient.sendAsync(request, BodyHandlers.ofString()).thenApply(HttpResponse::body)
				.thenAccept(System.out::println).exceptionally(e -> handleError(e));

		return List.of();
	}

	private static Void handleError(Throwable e) {
		throwable = e;
		return null;
	}
}
