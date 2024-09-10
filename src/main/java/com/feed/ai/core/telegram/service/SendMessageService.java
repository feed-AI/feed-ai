package com.feed.ai.core.telegram.service;

import com.feed.ai.core.exception.MessageFailedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

@Service
@Slf4j
public class SendMessageService {

    private final HttpClient httpClient;

    @Value("${telegram.apiKey}")
    private String telegramBotToken;

    @Value("${telegram.chatId}")
    private String telegramChatId;

    public SendMessageService() {
        this.httpClient = HttpClient.newHttpClient();
    }

    @Retryable(interceptor = "retryData", retryFor = {IOException.class})
    public void sendMessageToTelegram(String data) {
        String urlString = String.format("https://api.telegram.org/bot%s/sendMessage", telegramBotToken);

        String postData = String.format("chat_id=%s&text=%s", telegramChatId, data);
        log.info("Post Data: {}", postData);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(urlString))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .POST(HttpRequest.BodyPublishers.ofString(postData, StandardCharsets.UTF_8))
                .build();

        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                log.info("Response: {}", response.body());
            } else {
                log.error("Failed to send message. Response Code: {}", response.statusCode());
            }
        } catch (IOException e) {
            log.error("Error occurred while sending message: ", e);
            throw new MessageFailedException("Error occurred while sending message: {}", e);
        } catch (InterruptedException e) {
            log.error("Thread was interrupted while sending message: ", e);
            Thread.currentThread().interrupt();
        }
    }
}
