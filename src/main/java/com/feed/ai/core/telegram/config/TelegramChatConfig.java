package com.feed.ai.core.telegram.config;

import com.feed.ai.core.telegram.service.PollingTelegramService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Configuration
public class TelegramChatConfig {

    private final PollingTelegramService pollingTelegramService;

    public TelegramChatConfig(PollingTelegramService pollingTelegramService) {
        this.pollingTelegramService = pollingTelegramService;
    }

    @Bean
    public TelegramBotsApi telegramBotsApi() throws TelegramApiException {
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        telegramBotsApi.registerBot(pollingTelegramService);
        return telegramBotsApi;
    }
}
