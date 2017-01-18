package ru.ifmo.quant.commands;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.ifmo.quant.HandlingProcess;
import ru.ifmo.quant.KeyboardEnum;
import ru.ifmo.quant.OutputMessage;
import ru.ifmo.quant.QuantMessage;
import ru.ifmo.quant.entities.AccountEntity;

import java.sql.Time;
import java.util.*;

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
            stringBuilder.append(ctx.getMessage("command.start", null, quantLocale.DEFAULT)).append("\n");
            AccountEntity accountEntity = new AccountEntity();
            accountEntity.insertKey(input.getMessageAddress());
            accountEntity.setTimeZoneOffset(getTimeZoneOffset(input));
            accountEntity.setLanguage(quantLocale.DEFAULT.getLanguage());
            dataService.save(accountEntity);
            process.setAccountEntity(accountEntity);
            //TODO: line below is test
            stringBuilder.append("\n"+accountEntity.getTimeZoneOffset()+"\n");
        } else {
            stringBuilder.append(ctx.getMessage("command.start.again", null, process.getAccountEntity().LOCALE));
        }
        QuantMessage answer = new OutputMessage(input, stringBuilder.toString());
        output.add(answer);
        answer.setKeyboard(KeyboardEnum.DEFAULT);
        return output;
    }

    public int getTimeZoneOffset(QuantMessage input) {
        Long clientTime = input.getDate();
        Calendar serverCalendar = new GregorianCalendar();
        TimeZone serverTimeZone = serverCalendar.getTimeZone();
        return serverTimeZone.getOffset(clientTime);
    }

}
