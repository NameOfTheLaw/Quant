package ru.ifmo.quant.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.ifmo.quant.entities.AccountEntity;
import ru.ifmo.quant.entities.TaskEntity;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by andrey on 05.11.2016.
 */
public interface TaskRepository extends CrudRepository<TaskEntity, Long> {

    List<TaskEntity> findByAccount(AccountEntity accountEntity);
    List<TaskEntity> findByAccountAndDateBefore(AccountEntity accountEntity, Timestamp time);
    List<TaskEntity> findByAccountAndDateBetween(AccountEntity accountEntity, Timestamp timeStart, Timestamp timeEnd);
    List<TaskEntity> findByDateBefore(Timestamp time);

}