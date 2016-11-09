package ru.ifmo.quant.commands;

import ru.ifmo.quant.HandlingProcess;
import ru.ifmo.quant.QuantMessage;
import ru.ifmo.quant.entity.AccountEntity;
import ru.ifmo.quant.exceptions.WrongContextCommandException;

/**
 * Created by andrey on 09.11.2016.
 */
public class StartCommand extends AbstractCommand {

    public String perform(QuantMessage input, AccountEntity account, HandlingProcess process) throws WrongContextCommandException {
        StringBuilder stringBuilder;
        if (process == null) {
            stringBuilder = new StringBuilder();
            if (account == null) {
                stringBuilder.append("I see you new here! I register you.").append("\n");
                AccountEntity accountEntity = new AccountEntity();
                accountEntity.insertKey(input.getMessageAddress());
                accountDao.save(accountEntity);
            } else {
                stringBuilder.append("Nice to meet you!");
            }
        } else {
            throw new WrongContextCommandException("\\start command must not have any running processes");
        }
        return stringBuilder.toString();
    }
}
