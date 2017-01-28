package ru.ifmo.quant;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import ru.ifmo.quant.entities.AccountEntity;
import ru.ifmo.quant.entities.NotificationEntity;
import ru.ifmo.quant.entities.TaskEntity;

/**
 * Created by andrey on 28.01.2017.
 */
@Component
public class QuantFormatter implements ApplicationContextAware {

    @Autowired
    private DateTimeService dateTimeService;
    @Autowired
    private QuantLocaleService quantLocaleService;
    @Autowired
    private ApplicationContext ctx;

    public String format(AccountEntity accountEntity) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(ctx.getMessage("account.language", new Object[] {accountEntity.getLanguage()}, quantLocaleService.getLocale(accountEntity)))
                .append(ctx.getMessage("account.timezone", new Object[] {accountEntity.getTimeZone()}, quantLocaleService.getLocale(accountEntity)))
                .append("\n");
        return stringBuilder.toString();
    }

    public String format(TaskEntity taskEntity) {
        return format(taskEntity, taskEntity.getAccount());
    }

    public String format(TaskEntity taskEntity, AccountEntity accountEntity) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(taskEntity.getBody())
                .append("\n")
                .append(dateTimeService.convertTimeZone(taskEntity.getDate(), accountEntity))
                .append("\n");
        return stringBuilder.toString();
    }

    public String format(NotificationEntity notificationEntity) {
        return format(notificationEntity, notificationEntity.getTask().getAccount());
    }

    public String format(NotificationEntity notificationEntity, AccountEntity accountEntity) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(notificationEntity.getDate())
                .append("\n[")
                .append(notificationEntity.getTask().getBody())
                .append("\n")
                .append(dateTimeService.convertTimeZone(notificationEntity.getTask().getDate(), accountEntity))
                .append("]\n");
        return stringBuilder.toString();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.ctx = applicationContext;
    }
}
