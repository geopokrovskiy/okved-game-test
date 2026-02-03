package com.geopokrovskiy.okved_game_test.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.geopokrovskiy.okved_game_test.entity.OkvedItem;
import com.geopokrovskiy.okved_game_test.entity.OkvedNode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
@Data
public class OkvedScheduler {

    @Value("${okved.fetch.url}")
    private static final String OKVED_URL = "https://raw.githubusercontent.com/bergstar/testcase/refs/heads/master/okved.json";
    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final ObjectMapper objectMapper;

    private final ArrayList<OkvedItem> items = new ArrayList<>();

    @Scheduled(cron = "${okved.fetch.cron}")
    public void getOkvedData() {
        try {
            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .uri(URI.create(OKVED_URL))
                    .GET()
                    .header("Accept", "application/json")
                    .build();

            log.info("Fetching the OKVED data from Github...");
            HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));

            if (response.statusCode() != 200) {
                log.error("Error during data fetch: {} ", response.body());
                throw new IOException("HTTP error: " + response.statusCode());
            }

            log.info("The data has been successfully fetched");
            String responseBody = response.body();
            processResponseBody(responseBody);

        } catch (IOException exception) {
            log.error("IO exception during data fetch: {} ", exception.getMessage());
        } catch (InterruptedException exception) {
            log.error("Interrupted exception during data fetch: {} ", exception.getMessage());
        }
    }

    public void processResponseBody(String responseBody) throws JsonProcessingException {
        List<OkvedNode> itemRoots = objectMapper.readValue(responseBody, new TypeReference<>() {
        }); // получаем корневые элементы нашего json-файла

        for (OkvedNode root : itemRoots) {
            collectAllItems(root, this.items); // рекурсивно собираем все остальные элементы нашего json-файла
        }
    }

    private static void collectAllItems(OkvedNode node, List<OkvedItem> result) {
        result.add(new OkvedItem(node.getCode(), node.getName()));
        if (node.getItems() == null || node.getItems().isEmpty()) {
            return;
        }

        for (OkvedNode child : node.getItems()) {
            collectAllItems(child, result);
        }
    }
}
