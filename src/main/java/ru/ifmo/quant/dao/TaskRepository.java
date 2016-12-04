package ru.ifmo.quant.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.ifmo.quant.entity.AccountEntity;
import ru.ifmo.quant.entity.TaskEntity;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * Created by andrey on 05.11.2016.
 */
public interface TaskRepository extends CrudRepository<TaskEntity, Long> {

    List<TaskEntity> findByAccount(AccountEntity accountEntity);

    //@Query("select t FROM TaskEntity t inner join t.account ac where t.serverDate < :currPeriod and ac.id = :accountId")
    @Query("select t FROM TaskEntity t where t.serverDate < :currPeriod and t.account.id = :accountId")
    List<TaskEntity> findByServerDateAndAccount_Id(@Param("currPeriod") Timestamp currPeriod, @Param("accountId") Long accountId);
}