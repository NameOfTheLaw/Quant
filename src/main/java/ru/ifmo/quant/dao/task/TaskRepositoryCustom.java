package ru.ifmo.quant.dao.task;

import ru.ifmo.quant.entity.AccountEntity;
import ru.ifmo.quant.entity.TaskEntity;

import java.util.Date;

/**
 * Created by andrey on 05.11.2016.
 */
public interface TaskRepositoryCustom {

    TaskEntity findById(Long id);
    TaskEntity findByAccount(AccountEntity accountEntity);
    TaskEntity findByServerDate(Date serverDate, Long period);
}
