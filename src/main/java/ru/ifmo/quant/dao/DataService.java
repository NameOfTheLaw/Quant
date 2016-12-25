package ru.ifmo.quant.dao;

import ru.ifmo.quant.QuantMessage;
import ru.ifmo.quant.entities.AccountEntity;
import ru.ifmo.quant.entities.NotificationEntity;
import ru.ifmo.quant.entities.TaskEntity;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * Created by andrey on 21.11.2016.
 */
public interface DataService {

    public AccountEntity findAccountEntity(Long id);
    public AccountEntity findAccountEntity(QuantMessage message);
    public AccountEntity findAccountEntityByKey(String socialKey, Long key);
    public Iterable<AccountEntity> findAllAccountEntities();
    public AccountEntity save(AccountEntity entity);
    public void delete(AccountEntity entity);
    public void deleteAllAccounts();
    public Long countAccountEntities();

    public NotificationEntity findNotificationEntity(Long id);
    public List<NotificationEntity> findNotificationEntity(Timestamp serverDate, Long period);
    public List<NotificationEntity> findNotificationEntity(TaskEntity taskEntity);
    public NotificationEntity save(NotificationEntity entity);
    public void delete(NotificationEntity entity);
    public void deleteAllNotifications();
    public Long countNotificationEntities();

    public TaskEntity findTaskEntity(Long id);
    public List<TaskEntity> findTaskEntity(AccountEntity accountEntity);
    public List<TaskEntity> findTaskEntity(Timestamp serverDate, Long period);
    public List<TaskEntity> findTaskEntity(Timestamp serverDate, Long period, AccountEntity accountEntity);
    public TaskEntity save(TaskEntity entity);
    public void delete(TaskEntity entity);
    public void deleteAllTasks();
    public Long countTaskEntities();

    public List<TaskEntity> findTaskEntityForToday(Timestamp date, AccountEntity accountEntity);
    public List<TaskEntity> findTaskEntityForWeek(Timestamp date, AccountEntity accountEntity);
}
