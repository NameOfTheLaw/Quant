package ru.ifmo.quant.dao.notification;

import ru.ifmo.quant.dao.EntityDao;
import ru.ifmo.quant.entity.NotificationEntity;
import ru.ifmo.quant.entity.TaskEntity;

import java.util.Date;

/**
 * Created by andrey on 05.11.2016.
 */
public interface NotificationDao extends EntityDao<NotificationEntity>, NotificationRepositoryCustom {
}
