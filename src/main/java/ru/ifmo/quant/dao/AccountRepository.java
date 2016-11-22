package ru.ifmo.quant.dao;

import org.springframework.data.repository.CrudRepository;
import ru.ifmo.quant.entity.AccountEntity;

/**
 * Created by andrey on 04.11.2016.
 */
public interface AccountRepository extends CrudRepository<AccountEntity, Long>{

    AccountEntity findByVkKey(Long key);
    AccountEntity findByTelegramKey(Long key);

}
