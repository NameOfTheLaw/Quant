package ru.ifmo.quant.dao;

import org.springframework.data.repository.CrudRepository;
import ru.ifmo.quant.entity.AccountEntity;
import ru.ifmo.quant.entity.TaskEntity;

import java.util.Date;
import java.util.List;

/**
 * Created by andrey on 05.11.2016.
 */
public interface TaskRepository extends CrudRepository<TaskEntity, Long> {

    List<TaskEntity> findByAccount(AccountEntity accountEntity);
    List<TaskEntity> findByServerDate(Date serverDate, Long period);
}
