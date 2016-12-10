package ru.ifmo.quant.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.ifmo.quant.entities.NotificationEntity;
import ru.ifmo.quant.entities.TaskEntity;

import java.util.Date;
import java.util.List;

/**
 * Created by andrey on 05.11.2016.
 */
public interface NotificationRepository extends CrudRepository<NotificationEntity, Long> {

    @Query("select n FROM NotificationEntity n where n.serverDate < :currPeriod")
    List<NotificationEntity> findByServerDate(@Param("currPeriod") Date currPeriod);
    List<NotificationEntity> findByTask(TaskEntity taskEntity);

}
