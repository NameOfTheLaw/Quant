package ru.ifmo.quant.dao.notification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ifmo.quant.entity.NotificationEntity;
import ru.ifmo.quant.entity.TaskEntity;

import java.util.Date;

/**
 * Created by andrey on 05.11.2016.
 */
@Service("notificationDao")
@Repository
@Transactional
public class NotificationDaoImpl implements NotificationDao {

    @Autowired
    NotificationRepository notificationRepository;

    public NotificationEntity findById(Long id) {
        return notificationRepository.findOne(id);
    }

    public NotificationEntity findByServerDate(Date serverDate, Long period) {
        //TODO: realise NotificationDaoImpl.findByServerDate method
        return null;
    }

    public NotificationEntity findByTask(TaskEntity taskEntity) {
        return notificationRepository.findByTask(taskEntity);
    }

    public boolean exists(Long id) {
        return notificationRepository.exists(id);
    }

    public NotificationEntity save(NotificationEntity entity) {
        return notificationRepository.save(entity);
    }

    public void delete(NotificationEntity entity) {
        notificationRepository.delete(entity);
    }
}
