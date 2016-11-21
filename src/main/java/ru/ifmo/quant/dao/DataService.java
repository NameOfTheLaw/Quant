package ru.ifmo.quant.dao;

import ru.ifmo.quant.MessageAddress;
import ru.ifmo.quant.QuantMessage;
import ru.ifmo.quant.entity.AccountEntity;
import ru.ifmo.quant.entity.NotificationEntity;
import ru.ifmo.quant.entity.TaskEntity;

import java.util.Date;
import java.util.List;

/**
 * Created by andrey on 21.11.2016.
 */
public interface DataService {

    public AccountEntity findAccountEntity(Long id);
    public AccountEntity findAccountEntity(QuantMessage message);
    public AccountEntity findAccountEntityByKey(String socialKey, Long key);
    public AccountEntity save(AccountEntity entity);
    public void delete(AccountEntity entity);

    public NotificationEntity findNotificationEntity(Long id);
    public List<NotificationEntity> findNotificationEntity(Date serverDate, Long period);
    public List<NotificationEntity> findNotificationEntity(TaskEntity taskEntity);
    public NotificationEntity save(NotificationEntity entity);
    public void delete(NotificationEntity entity);

    public TaskEntity findTaskEntity(Long id);
    public List<TaskEntity> findTaskEntity(AccountEntity accountEntity);
    public List<TaskEntity> findTaskEntity(Date serverDate, Long period);
    public TaskEntity save(TaskEntity entity);
    public void delete(TaskEntity entity);

}
