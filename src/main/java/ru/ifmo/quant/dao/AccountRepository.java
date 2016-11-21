package ru.ifmo.quant.dao;

import org.springframework.data.repository.CrudRepository;
import ru.ifmo.quant.entity.AccountEntity;

/**
 * Created by andrey on 04.11.2016.
 */
public interface AccountRepository extends CrudRepository<AccountEntity, Long>{

    AccountEntity findOneIgnoreTask(Long id);
    AccountEntity findByVkKeyIgnoreTask(Long key);
    AccountEntity findByTelegramKeyIgnoreTask(Long key);

}
