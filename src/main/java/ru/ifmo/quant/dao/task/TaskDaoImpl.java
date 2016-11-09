package ru.ifmo.quant.dao.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ifmo.quant.entity.AccountEntity;
import ru.ifmo.quant.entity.TaskEntity;

import java.util.Date;

/**
 * Created by andrey on 05.11.2016.
 */
@Service("taskDao")
@Repository
@Transactional
public class TaskDaoImpl implements TaskDao {

    @Autowired
    TaskRepository taskRepository;

    public TaskEntity findById(Long id) {
        return taskRepository.findOne(id);
    }

    public TaskEntity findByAccount(AccountEntity accountEntity) {
        return taskRepository.findByAccount(accountEntity);
    }

    public TaskEntity findByServerDate(Date serverDate, Long period) {
        //TODO: realise TaskDaoImpl.findByServerDate method
        return null;
    }

    public boolean exists(Long id) {
        return taskRepository.exists(id);
    }

    public TaskEntity save(TaskEntity entity) {
        return taskRepository.save(entity);
    }

    public void delete(TaskEntity entity) {
        taskRepository.delete(entity);
    }
}
