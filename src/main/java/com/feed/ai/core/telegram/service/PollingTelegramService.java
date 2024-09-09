package com.feed.ai.core.telegram.service;

import com.feed.ai.core.exception.MessageFailedException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
public class PollingTelegramService extends TelegramLongPollingBot {

    private final String botUsername;

    public PollingTelegramService(@Value("${telegram.apiKey}") String botToken,
                                  @Value("${telegram.botUsername}") String botUsername) {
        super(botToken);
        this.botUsername = botUsername;
    }

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public void onUpdateReceived(Update update) {
        SendMessage sendMessage = new SendMessage();
        String command = update.getMessage().getText();
        sendMessage.setChatId(update.getMessage().getChatId());
        //create a service layer logic to send the string message
        String response = String.valueOf(command);
        try {
            sendMessage.setText(response);
            execute(sendMessage);
        } catch (Exception e) {
            throw new MessageFailedException("Error while handling message : {}", e);
        }
    }
}
