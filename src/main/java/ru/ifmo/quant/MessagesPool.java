package ru.ifmo.quant;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
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
@Component
public class MessagesPool implements ApplicationContextAware {

    @Value("${messagespool.refreshingsize}")
    private int MESSAGES_POOL_SIZE;
    @Value("${messagespool.notificationloadperiod}")
    private Long NOTIFICATION_LOAD_PERIOD;
    @Value("${messagespool.taskloadperiod}")
    private Long TASK_LOAD_PERIOD;

    private ApplicationContext ctx;
    @Autowired
    private TelegramHandler telegramHandler;
    @Autowired
    private DataService dataService;
    @Autowired
    private QuantLocaleService quantLocaleService;
    @Autowired
    private QuantFormatter quantFormatter;

    private Queue<QuantMessage> messagesPool = new LinkedList<QuantMessage>();

    @Scheduled(fixedRateString = "${messagespool.refreshingtime}")
    private void cleanPool() {
        for (int i = 0; i<MESSAGES_POOL_SIZE && !messagesPool.isEmpty(); i++) {
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

    @Scheduled(fixedRateString = "${messagespool.notificationgettime}")
    public void addNotificationMessagesToPool() {
        List<NotificationEntity> notifications = dataService.findNotificationEntity(new Timestamp(System.currentTimeMillis()), NOTIFICATION_LOAD_PERIOD);
        if (notifications!=null) {
            for (NotificationEntity entity: notifications) {
                QuantMessage message = new OutputMessage();
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(ctx.getMessage("messagepool.notification", null, quantLocaleService.getLocale(entity.getTask().getAccount().getLanguage())))
                        .append(quantFormatter.format(entity.getTask()));
                message.setText(stringBuilder.toString());
                message.setMessageAddress(new MessageAddress(MessageAddress.TELEGRAM_ALIAS, entity.getTask().getAccount().getTelegramKey()));
                dataService.delete(entity);
                addToPool(message);
            }
        }
    }

    @Scheduled(fixedRateString = "${messagespool.taskgettime}")
    public void addTaskMessagesToPool() {
        List<TaskEntity> taskEntities = dataService.findTaskEntity(new Timestamp(System.currentTimeMillis()), TASK_LOAD_PERIOD);
        if (taskEntities!=null) {
            for (TaskEntity entity: taskEntities) {
                QuantMessage message = new OutputMessage();
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(ctx.getMessage("messagepool.task", null, quantLocaleService.getLocale(entity.getAccount().getLanguage())))
                        .append(quantFormatter.format(entity));
                message.setText(stringBuilder.toString());
                message.setMessageAddress(new MessageAddress(MessageAddress.TELEGRAM_ALIAS, entity.getAccount().getTelegramKey()));
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

    public void addToPool(Queue<QuantMessage> messages) {
        try {
            messagesPool.addAll(messages);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.ctx = applicationContext;
    }
}
