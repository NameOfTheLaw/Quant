package ru.ifmo.quant.dao;

import org.springframework.beans.factory.annotation.Autowired;
import ru.ifmo.quant.MessageAddress;
import ru.ifmo.quant.QuantMessage;
import ru.ifmo.quant.commands.AbstractCommand;
import ru.ifmo.quant.entity.AccountEntity;
import ru.ifmo.quant.entity.NotificationEntity;
import ru.ifmo.quant.entity.TaskEntity;

import java.util.Date;
import java.util.List;

/**
 * Created by andrey on 21.11.2016.
 */
public class DataServiceImpl implements DataService {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    NotificationRepository notificationRepository;

    @Autowired
    TaskRepository taskRepository;

    public AccountEntity findAccountEntity(Long id) {
        return accountRepository.findOneIgnoreTask(id);
    }

    public AccountEntity findAccountEntity(QuantMessage message) {
        return findAccountEntityByKey(message.getMessageAddress().getSocial(),message.getMessageAddress().getKey());
    }

    public AccountEntity findAccountEntityByKey(String socialKey, Long key) {
        if (socialKey.equals(MessageAddress.TELEGRAM_ALIAS))
            return accountRepository.findByTelegramKeyIgnoreTask(key);
        if (socialKey.equals(MessageAddress.VK_ALIAS))
            return accountRepository.findByVkKeyIgnoreTask(key);
        return null;
    }

    public AccountEntity save(AccountEntity entity) {
        return accountRepository.save(entity);
    }

    public void delete(AccountEntity entity) {
        accountRepository.delete(entity);
    }

    public NotificationEntity findNotificationEntity(Long id) {
        return notificationRepository.findOne(id);
    }

    public List<NotificationEntity> findNotificationEntity(Date serverDate, Long period) {
        return notificationRepository.findByServerDate(serverDate, period);
    }

    public List<NotificationEntity> findNotificationEntity(TaskEntity taskEntity) {
        return notificationRepository.findByTask(taskEntity);
    }

    public NotificationEntity save(NotificationEntity entity) {
        return notificationRepository.save(entity);
    }

    public void delete(NotificationEntity entity) {
        notificationRepository.delete(entity);
    }

    public TaskEntity findTaskEntity(Long id) {
        return taskRepository.findOne(id);
    }

    public List<TaskEntity> findTaskEntity(AccountEntity accountEntity) {
        return taskRepository.findByAccount(accountEntity);
    }

    public List<TaskEntity> findTaskEntity(Date serverDate, Long period) {
        return taskRepository.findByServerDate(serverDate, period);
    }

    public TaskEntity save(TaskEntity entity) {
        return taskRepository.save(entity);
    }

    public void delete(TaskEntity entity) {
        taskRepository.delete(entity);
    }
}
