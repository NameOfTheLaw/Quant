package ru.ifmo.quant.commands;

import ru.ifmo.quant.HandlingProcess;
import ru.ifmo.quant.QuantMessage;
import ru.ifmo.quant.dao.DataService;
import ru.ifmo.quant.entity.AccountEntity;
import ru.ifmo.quant.exceptions.WrongContextCommandException;

/**
 * Created by andrey on 09.11.2016.
 */
public class StartCommand implements QuantCommand {

    public String perform(QuantMessage input, AccountEntity account, HandlingProcess process, DataService dataService) throws WrongContextCommandException {
        StringBuilder stringBuilder;
        stringBuilder = new StringBuilder();
        if (account == null) {
            stringBuilder.append("I see you new here! I register you.").append("\n");
            AccountEntity accountEntity = new AccountEntity();
            accountEntity.insertKey(input.getMessageAddress());
            dataService.save(accountEntity);
        } else {
            stringBuilder.append("Nice to meet you!");
        }
        return stringBuilder.toString();
    }
}
