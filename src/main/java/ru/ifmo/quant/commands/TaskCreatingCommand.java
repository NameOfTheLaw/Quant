package ru.ifmo.quant.commands;

import ru.ifmo.quant.DateExtractor;
import ru.ifmo.quant.HandleState;
import ru.ifmo.quant.HandlingProcess;
import ru.ifmo.quant.QuantMessage;
import ru.ifmo.quant.entity.AccountEntity;
import ru.ifmo.quant.entity.TaskEntity;
import ru.ifmo.quant.exceptions.WrongContextCommandException;

import java.util.Date;

/**
 * Created by andrey on 21.11.2016.
 */
public class TaskCreatingCommand extends AbstractCommand {

    public String perform(QuantMessage input, AccountEntity account, HandlingProcess process) throws WrongContextCommandException {
        String rawText = input.getText();
        DateExtractor dateExtractor = new DateExtractor(rawText);
        Date date = dateExtractor.getDate();
        String answer = null;
        if (date != null) {
            TaskEntity taskEntity = new TaskEntity();
            taskEntity.setAccount(account);
            taskEntity.setBody(dateExtractor.getText());
            taskEntity.setServerDate(date);
            taskEntity.setClientDate(date);
            dataService.save(taskEntity);
            answer = "Ok. Do you want me to set notice? Just tell me time or ignore me";
            process.setHandleState(HandleState.CREATING_NOTIFICATION);
        } else {
            answer = "Could you please repeat. I can't recognise date";
            process.setHandleState(HandleState.CONFIRMATION_TASK);
        }
        return answer;
    }

}
