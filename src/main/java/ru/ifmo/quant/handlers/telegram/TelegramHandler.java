package ru.ifmo.quant.handlers.telegram;

import org.springframework.beans.factory.InitializingBean;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import ru.ifmo.quant.InputMessage;
import ru.ifmo.quant.QuantMessage;
import ru.ifmo.quant.handlers.MessageHandler;

/**
 * Created by andrey on 05.11.2016.
 */
public class TelegramHandler extends TelegramLongPollingBot implements InitializingBean {

    private String botName;
    private String botToken;
    private TelegramBotsApi telegramBotsApi;
    private MessageHandler messageHandler;

    public void afterPropertiesSet() throws Exception {
        telegramBotsApi.registerBot(this);
    }

    public void onUpdateReceived(Update update) {
        QuantMessage message = new InputMessage(update);
        messageHandler.update(message);
    }

    public String getBotUsername() {
        return botName;
    }

    public String getBotToken() {
        return botToken;
    }

    public String getBotName() {
        return botName;
    }

    public void setBotName(String botName) {
        this.botName = botName;
    }

    public void setBotToken(String botToken) {
        this.botToken = botToken;
    }

    public TelegramBotsApi getTelegramBotsApi() {
        return telegramBotsApi;
    }

    public void setTelegramBotsApi(TelegramBotsApi telegramBotsApi) {
        this.telegramBotsApi = telegramBotsApi;
    }

    public MessageHandler getMessageHandler() {
        return messageHandler;
    }

    public void setMessageHandler(MessageHandler messageHandler) {
        this.messageHandler = messageHandler;
    }
}
