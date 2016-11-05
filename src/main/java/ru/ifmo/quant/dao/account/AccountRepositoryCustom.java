package ru.ifmo.quant.dao.account;

import ru.ifmo.quant.entity.AccountEntity;

/**
 * Created by andrey on 05.11.2016.
 */
public interface AccountRepositoryCustom {

    AccountEntity findById(Long id);
    AccountEntity findByVkKey(Long key);
    AccountEntity findByTelegramKey(Long key);
}
