package com.mappedswap.telegrambot;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class Main {

    // main
    public static void main(String[] args) {
        try{
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(new MappedSwap_Chatbot());
        } catch (TelegramApiException exception) {
            exception.printStackTrace();
        }
    }

}
