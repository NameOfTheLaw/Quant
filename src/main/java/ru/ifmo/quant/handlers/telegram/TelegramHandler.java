package ru.ifmo.quant.handlers.telegram;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import ru.ifmo.quant.InputMessage;
import ru.ifmo.quant.QuantMessage;
import ru.ifmo.quant.handlers.MessageHandler;

/**
 * Created by andrey on 05.11.2016.
 */
@Component
public class TelegramHandler extends TelegramLongPollingBot implements InitializingBean {

    @Value("${telegram.botusername}")
    private String botName;
    @Value("${telegram.bottoken}")
    private String botToken;
    @Autowired
    private MessageHandler messageHandler;
    private TelegramBotsApi telegramBotsApi;

    public void afterPropertiesSet() throws Exception {
        telegramBotsApi = new TelegramBotsApi();
        telegramBotsApi.registerBot(this);
    }

    public void onUpdateReceived(Update update) {
        messageHandler.update(new InputMessage(update));
    }

    public String getBotUsername() {
        return botName;
    }

    public String getBotToken() {
        return botToken;
    }

}
