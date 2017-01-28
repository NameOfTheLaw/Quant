package ru.ifmo.quant.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.ifmo.quant.MessageAddress;
import ru.ifmo.quant.QuantLocaleService;
import ru.ifmo.quant.QuantMessage;
import ru.ifmo.quant.DateTimeService;
import ru.ifmo.quant.entities.AccountEntity;
import ru.ifmo.quant.entities.NotificationEntity;
import ru.ifmo.quant.entities.TaskEntity;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.WeekFields;

/**
 * Created by andrey on 21.11.2016.
 */
@Component("dataService")
public class DataServiceImpl implements DataService {

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private DateTimeService dateTimeService;
    @Autowired
    private QuantLocaleService quantLocaleService;

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

    public List<NotificationEntity> findNotificationEntity(TaskEntity taskEntity, Date serverDate, Long period) {
        return notificationRepository.findByTaskAndDateBefore(taskEntity, new Timestamp(serverDate.getTime()+period));
    }

    public List<NotificationEntity> findNotificationEntity(TaskEntity taskEntity) {
        return notificationRepository.findByTask(taskEntity);
    }

    public List<NotificationEntity> findNotificationEntity(AccountEntity accountEntity, Date serverDate, Long period) {
        return notificationRepository.findByAccountAndDateBefore(accountEntity, new Timestamp(serverDate.getTime()+period));
    }

    public List<NotificationEntity> findNotificationEntity(Date serverDate, Long period) {
        return notificationRepository.findByDateBefore(new Timestamp(serverDate.getTime()+period));
    }

    public NotificationEntity save(NotificationEntity entity) {
        return notificationRepository.save(entity);
    }

    public void delete(NotificationEntity entity) {
        notificationRepository.delete(entity);
    }

    public void deleteAllNotifications() {
        notificationRepository.deleteAll();
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

    public List<TaskEntity> findTaskEntity(AccountEntity accountEntity, Date serverDate, Long period) {
        return taskRepository.findByAccountAndDateBefore(accountEntity, new Timestamp(serverDate.getTime()+period));
    }

    public List<TaskEntity> findTaskEntity(Date serverDate, Long period) {
        return taskRepository.findByDateBefore(new Timestamp(serverDate.getTime()+period));
    }

    public TaskEntity save(TaskEntity entity) {
        return taskRepository.save(entity);
    }

    public void delete(TaskEntity entity) {
        taskRepository.delete(entity);
    }

    public void deleteAllTasks() {
        taskRepository.deleteAll();
    }

    public Long countTaskEntities() {
        return taskRepository.count();
    }

    public List<TaskEntity> findTaskEntityForWeek(AccountEntity accountEntity) {
        ZoneId serverZoneId = ZoneId.of(dateTimeService.getDefaultTimeZone().getID());
        ZoneId clientZoneId = ZoneId.of(accountEntity.getTimeZone());
        LocalDateTime localDateTime = LocalDateTime.now();

        ZonedDateTime serverDateTime = localDateTime.atZone(serverZoneId);
        ZonedDateTime clientDateTime = serverDateTime.withZoneSameInstant(clientZoneId);

        ZonedDateTime clientStartDateTime = clientDateTime
                .with(WeekFields.of(quantLocaleService.getLocale(accountEntity)).dayOfWeek(), 1)
                .withHour(0)
                .withMinute(0)
                .withSecond(0);
        ZonedDateTime clientEndDateTime = clientDateTime
                .with(WeekFields.of(quantLocaleService.getLocale(accountEntity)).dayOfWeek(), 7)
                .withHour(0)
                .withMinute(0)
                .withSecond(0);

        Timestamp timeStart = Timestamp.from(clientStartDateTime.withZoneSameInstant(serverZoneId).toInstant());
        Timestamp timeEnd = Timestamp.from(clientEndDateTime.withZoneSameInstant(serverZoneId).toInstant());
        return taskRepository.findByAccountAndDateBetween(accountEntity, timeStart, timeEnd);
    }

    public List<TaskEntity> findTaskEntityForToday(AccountEntity accountEntity) {
        ZoneId serverZoneId = ZoneId.of(dateTimeService.getDefaultTimeZone().getID());
        ZoneId clientZoneId = ZoneId.of(accountEntity.getTimeZone());
        LocalDateTime localDateTime = LocalDateTime.now();

        ZonedDateTime serverDateTime = localDateTime.atZone(serverZoneId);
        ZonedDateTime clientDateTime = serverDateTime.withZoneSameInstant(clientZoneId);

        ZonedDateTime clientStartDateTime = clientDateTime
                .withHour(0)
                .withMinute(0)
                .withSecond(0);
        ZonedDateTime clientEndDateTime = clientDateTime
                .withHour(23)
                .withMinute(59)
                .withSecond(59);

        Timestamp timeStart = Timestamp.from(clientStartDateTime.withZoneSameInstant(serverZoneId).toInstant());
        Timestamp timeEnd = Timestamp.from(clientEndDateTime.withZoneSameInstant(serverZoneId).toInstant());
        return taskRepository.findByAccountAndDateBetween(accountEntity, timeStart, timeEnd);
    }
}
