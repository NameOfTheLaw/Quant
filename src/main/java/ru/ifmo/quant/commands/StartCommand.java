package ru.ifmo.quant.commands;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.ifmo.quant.HandlingProcess;
import ru.ifmo.quant.QuantMessage;
import ru.ifmo.quant.entities.AccountEntity;

/**
 * Created by andrey on 09.11.2016.
 */
@Component
@Scope("prototype")
public class StartCommand extends QuantCommand {

    public String perform(QuantMessage input, HandlingProcess process) {
        StringBuilder stringBuilder;
        stringBuilder = new StringBuilder();
        if (process.getAccountEntity() == null) {
            stringBuilder.append(ctx.getMessage("command.start", null, input.getLocale())).append("\n");
            AccountEntity accountEntity = new AccountEntity();
            accountEntity.insertKey(input.getMessageAddress());
            dataService.save(accountEntity);
        } else {
            stringBuilder.append(ctx.getMessage("command.start.again", null, input.getLocale()));
        }
        return stringBuilder.toString();
    }
}
