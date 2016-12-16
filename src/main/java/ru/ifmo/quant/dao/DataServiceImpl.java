package ru.ifmo.quant.dao;

import org.springframework.beans.factory.annotation.Autowired;
import ru.ifmo.quant.MessageAddress;
import ru.ifmo.quant.QuantMessage;
import ru.ifmo.quant.entities.AccountEntity;
import ru.ifmo.quant.entities.NotificationEntity;
import ru.ifmo.quant.entities.TaskEntity;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
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
        return accountRepository.findOne(id);
    }

    public AccountEntity findAccountEntity(QuantMessage message) {
        return findAccountEntityByKey(message.getMessageAddress().getSocial(),message.getMessageAddress().getKey());
    }

    public AccountEntity findAccountEntityByKey(String socialKey, Long key) {
        if (socialKey.equals(MessageAddress.TELEGRAM_ALIAS))
            return accountRepository.findByTelegramKey(key);
        if (socialKey.equals(MessageAddress.VK_ALIAS))
            return accountRepository.findByVkKey(key);
        return null;
    }

    public Iterable<AccountEntity> findAllAccountEntities() {
        return accountRepository.findAll();
    }

    public AccountEntity save(AccountEntity entity) {
        return accountRepository.save(entity);
    }

    public void delete(AccountEntity entity) {
        accountRepository.delete(entity);
    }

    public Long countAccountEntities() {
        return accountRepository.count();
    }

    public NotificationEntity findNotificationEntity(Long id) {
        return notificationRepository.findOne(id);
    }

    public List<NotificationEntity> findNotificationEntity(Date serverDate, Long period) {
        return notificationRepository.findByServerDate(new Timestamp(serverDate.getTime()+period));
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

    public Long countNotificationEntities() {
        return notificationRepository.count();
    }

    public TaskEntity findTaskEntity(Long id) {
        return taskRepository.findOne(id);
    }

    public List<TaskEntity> findTaskEntity(AccountEntity accountEntity) {
        return taskRepository.findByAccount(accountEntity);
    }

    public List<TaskEntity> findTaskEntity(Date serverDate, Long period, AccountEntity accountEntity) {
        return taskRepository.findByServerDateAndAccount_Id(new Timestamp(serverDate.getTime()+period), accountEntity.getId());
    }

    public TaskEntity save(TaskEntity entity) {
        return taskRepository.save(entity);
    }

    public void delete(TaskEntity entity) {
        taskRepository.delete(entity);
    }

    public Long countTaskEntities() {
        return taskRepository.count();
    }

    public List<TaskEntity> findTaskEntityForToday(Date date, AccountEntity accountEntity) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.HOUR, 0);
        Timestamp timeStart = new Timestamp(calendar.getTime().getTime());
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Timestamp timeEnd = new Timestamp(calendar.getTime().getTime());
        return taskRepository.findByServerDateBetween(timeStart, timeEnd);
    }

    public List<TaskEntity> findTaskEntityForWeek(Date date, AccountEntity accountEntity) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        Timestamp timeStart = new Timestamp(calendar.getTime().getTime());
        calendar.add(Calendar.WEEK_OF_YEAR, 1);
        Timestamp timeEnd = new Timestamp(calendar.getTime().getTime());
        return taskRepository.findByServerDateBetween(timeStart, timeEnd);
    }
}
