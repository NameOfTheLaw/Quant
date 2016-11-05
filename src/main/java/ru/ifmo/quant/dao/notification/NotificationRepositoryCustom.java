package ru.ifmo.quant.dao.notification;

import ru.ifmo.quant.entity.NotificationEntity;
import ru.ifmo.quant.entity.TaskEntity;

import java.util.Date;

/**
 * Created by andrey on 05.11.2016.
 */
public interface NotificationRepositoryCustom {

    NotificationEntity findById(Long id);
    NotificationEntity findByServerDate(Date serverDate, Long period);
    NotificationEntity findByTask(TaskEntity taskEntity);
}
