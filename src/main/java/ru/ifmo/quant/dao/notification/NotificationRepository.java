package ru.ifmo.quant.dao.notification;

import org.springframework.data.repository.CrudRepository;
import ru.ifmo.quant.entity.NotificationEntity;

/**
 * Created by andrey on 05.11.2016.
 */
public interface NotificationRepository extends CrudRepository<NotificationEntity, Long>, NotificationRepositoryCustom {

}
