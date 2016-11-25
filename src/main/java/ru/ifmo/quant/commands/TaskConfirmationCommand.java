package ru.ifmo.quant.commands;

import ru.ifmo.quant.DateExtractor;
import ru.ifmo.quant.HandleState;
import ru.ifmo.quant.HandlingProcess;
import ru.ifmo.quant.QuantMessage;
import ru.ifmo.quant.dao.DataService;
import ru.ifmo.quant.entity.AccountEntity;
import ru.ifmo.quant.entity.TaskEntity;

import java.util.Date;

/**
 * Created by andrey on 25.11.2016.
 */
public class TaskConfirmationCommand extends TaskCreatingCommand {
    public String perform(QuantMessage input, AccountEntity account, HandlingProcess process, DataService dataService) {
        String rawText = input.getText();
        DateExtractor dateExtractor = new DateExtractor(rawText);
        Date date = dateExtractor.getDate();
        TaskEntity taskEntity = process.getParameter("new-task", TaskEntity.class);
        String answer = controlFlow(date, taskEntity, process, dataService);
        return answer;
    }
}
