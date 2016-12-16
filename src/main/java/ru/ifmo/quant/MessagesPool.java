package ru.ifmo.quant;

import org.springframework.scheduling.annotation.Scheduled;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardHide;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import ru.ifmo.quant.dao.DataService;
import ru.ifmo.quant.entities.AccountEntity;
import ru.ifmo.quant.entities.NotificationEntity;
import ru.ifmo.quant.entities.TaskEntity;
import ru.ifmo.quant.handlers.telegram.TelegramHandler;

import java.sql.Timestamp;
import java.util.*;

/**
 * Created by andrey on 26.11.2016.
 */
public class MessagesPool {

    private static final int MESSAGES_POOL_REFRESHING_TIME = 100;
    private static final int MESSAGES_POOL_SIZE = 6;
    private static final int NOTIFICATION_GET_TIME = 1000;
    private static final Long NOTIFICATION_LOAD_PERIOD = 2000l;
    //every day at 20:00
    private static final String TASKS_PROGRESS_NOTICING_CRON = "0 * 20 * * *";
    private Queue<QuantMessage> messagesPool = new LinkedList<QuantMessage>();
    private TelegramHandler telegramHandler;
    private DataService dataService;

    @Scheduled(fixedRate = MESSAGES_POOL_REFRESHING_TIME)
    private void cleanPool() {
        for (int i = 0; i<MESSAGES_POOL_SIZE && i<messagesPool.size(); i++) {
            QuantMessage message = messagesPool.poll();
            if (message.getMessageAddress().getSocial().equals(MessageAddress.TELEGRAM_ALIAS)) {
                SendMessage sendMessage = new SendMessage();
                sendMessage.setText(message.getText());
                sendMessage.setChatId(message.getMessageAddress().getKey().toString());
                sendMessage.enableMarkdown(true);
                if (message.isKeyboard()) sendMessage.setReplyMarkup(message.getKeyboard().getRows());
                try {
                    telegramHandler.sendMessage(sendMessage);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Scheduled(fixedRate = NOTIFICATION_GET_TIME)
    public void addNotificationMessagesToPool() {
        List<NotificationEntity> notifications = dataService.findNotificationEntity(new Timestamp(System.currentTimeMillis()), NOTIFICATION_LOAD_PERIOD);
        if (notifications!=null) {
            for (NotificationEntity entity: notifications) {
                QuantMessage message = new OutputMessage();
                message.setText("Notice you about "+entity.getTask());
                message.setMessageAddress(new MessageAddress(MessageAddress.TELEGRAM_ALIAS, entity.getTask().getAccount().getTelegramKey()));
                dataService.delete(entity);
                addToPool(message);
            }
        }
    }

    @Scheduled(cron = TASKS_PROGRESS_NOTICING_CRON)
    public void addProgressNotificationToPool() {
        //TODO: realise tasksProgressNoticing
    }

    public void addToPool(QuantMessage message) {
        try {
            messagesPool.add(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addToPool(Queue<QuantMessage> messages) {
        try {
            messagesPool.addAll(messages);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public TelegramHandler getTelegramHandler() {
        return telegramHandler;
    }

    public void setTelegramHandler(TelegramHandler telegramHandler) {
        this.telegramHandler = telegramHandler;
    }

    public DataService getDataService() {
        return dataService;
    }

    public void setDataService(DataService dataService) {
        this.dataService = dataService;
    }
}
