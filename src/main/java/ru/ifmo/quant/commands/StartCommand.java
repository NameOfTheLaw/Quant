package ru.ifmo.quant.commands;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.ifmo.quant.HandlingProcess;
import ru.ifmo.quant.KeyboardEnum;
import ru.ifmo.quant.OutputMessage;
import ru.ifmo.quant.QuantMessage;
import ru.ifmo.quant.entities.AccountEntity;

import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * Created by andrey on 09.11.2016.
 */
@Component
@Scope("prototype")
public class StartCommand extends QuantCommand {

    public Queue<QuantMessage> perform(QuantMessage input, HandlingProcess process) {
        StringBuilder stringBuilder = new StringBuilder();
        Queue<QuantMessage> output = new LinkedList<QuantMessage>();
        if (process.getAccountEntity() == null) {
            stringBuilder.append(ctx.getMessage("command.start", null, input.getLocale())).append("\n");
            AccountEntity accountEntity = new AccountEntity();
            accountEntity.insertKey(input.getMessageAddress());
            dataService.save(accountEntity);
        } else {
            stringBuilder.append(ctx.getMessage("command.start.again", null, input.getLocale()));
        }
        QuantMessage answer = new OutputMessage(input, stringBuilder.toString());
        output.add(answer);
        answer.setKeyboard(KeyboardEnum.DEFAULT_KEYBOARD);
        return output;
    }
}
