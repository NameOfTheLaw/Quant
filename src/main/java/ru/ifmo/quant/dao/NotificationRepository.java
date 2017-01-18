package ru.ifmo.quant.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.ifmo.quant.entities.AccountEntity;
import ru.ifmo.quant.entities.NotificationEntity;
import ru.ifmo.quant.entities.TaskEntity;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * Created by andrey on 05.11.2016.
 */
public interface NotificationRepository extends CrudRepository<NotificationEntity, Long> {

    List<NotificationEntity> findByTask(TaskEntity taskEntity);
    List<NotificationEntity> findByTaskAndDateBefore(TaskEntity taskEntity, Timestamp time);
    @Query("select n FROM TaskEntity t, NotificationEntity n where n.task = t and t.account = :account and n.date < :time")
    List<NotificationEntity> findByAccountAndDateBefore(@Param("account") AccountEntity accountEntity, @Param("time") Timestamp time);
    List<NotificationEntity> findByDateBefore(Timestamp time);

}
