package ru.ifmo.quant.dao.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ifmo.quant.MessageAddress;
import ru.ifmo.quant.QuantMessage;
import ru.ifmo.quant.entity.AccountEntity;

/**
 * Created by andrey on 04.11.2016.
 */
@Service("accountDao")
@Repository
@Transactional
public class AccountDaoImpl implements AccountDao {

    @Autowired
    private AccountRepository accountRepository;

    public AccountEntity findById(Long id) {
        return accountRepository.findOne(id);
    }

    public AccountEntity findByVkKey(Long key) {
        return accountRepository.findByVkKey(key);
    }

    public AccountEntity findByTelegramKey(Long key) {
        return accountRepository.findByTelegramKey(key);
    }

    public boolean exists(Long id) {
        return accountRepository.exists(id);
    }

    public AccountEntity save(AccountEntity entity) {
        return accountRepository.save(entity);
    }

    public void delete(AccountEntity entity) {
        accountRepository.delete(entity);
    }

    public AccountEntity findByQuantMessage(QuantMessage message) {
        if (message.getMessageAddress().getSocial().equals(MessageAddress.TELEGRAM_ALIAS))
            return findByTelegramKey(message.getMessageAddress().getKey());
        if (message.getMessageAddress().getSocial().equals(MessageAddress.VK_ALIAS))
            return findByVkKey(message.getMessageAddress().getKey());
        return null;
    }
}
