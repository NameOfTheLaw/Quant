package ru.ifmo.quant.dao.task;

import org.springframework.data.repository.CrudRepository;
import ru.ifmo.quant.entity.TaskEntity;

/**
 * Created by andrey on 05.11.2016.
 */
public interface TaskRepository extends CrudRepository<TaskEntity, Long>, TaskRepositoryCustom {
}
