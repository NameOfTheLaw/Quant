package ru.ifmo.quant.dao;

import org.springframework.data.repository.CrudRepository;
import ru.ifmo.quant.entity.NotificationEntity;
import ru.ifmo.quant.entity.TaskEntity;

import java.util.Date;
import java.util.List;

/**
 * Created by andrey on 05.11.2016.
 */
public interface NotificationRepository extends CrudRepository<NotificationEntity, Long> {

    List<NotificationEntity> findByServerDate(Date serverDate, Long period);
    List<NotificationEntity> findByTask(TaskEntity taskEntity);

}
