package ru.ifmo.quant;

import org.springframework.scheduling.annotation.Scheduled;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import ru.ifmo.quant.MessageAddress;
import ru.ifmo.quant.OutputMessage;
import ru.ifmo.quant.QuantMessage;
import ru.ifmo.quant.dao.DataService;
import ru.ifmo.quant.entity.NotificationEntity;
import ru.ifmo.quant.exceptions.WrongContextCommandException;
import ru.ifmo.quant.exceptions.WrongContextException;
import ru.ifmo.quant.handlers.telegram.TelegramHandler;

import java.sql.Timestamp;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * Created by andrey on 26.11.2016.
 */
public class MessagesPool {

    private static final int MESSAGES_POOL_REFRESHING_TIME = 200;
    private static final int MESSAGES_POOL_SIZE = 6;
    private static final int NOTIFICATION_GET_TIME = 1000;
    private static final Long NOTIFICATION_LOAD_PERIOD = 2000l;
    private Queue<QuantMessage> messagesPool = new PriorityQueue<QuantMessage>();
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
                try {
                    telegramHandler.sendMessage(sendMessage);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Scheduled(fixedRate = NOTIFICATION_GET_TIME)
    public void addNotificationMessagesToPool () {
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

    public void addToPool(QuantMessage message) {
        try {
            messagesPool.add(message);
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
