package ru.ifmo.quant.commands;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.ifmo.quant.HandlingProcess;
import ru.ifmo.quant.HandlingState;
import ru.ifmo.quant.QuantMessage;
import ru.ifmo.quant.entities.TaskEntity;
import ru.ifmo.quant.exceptions.BadCommandReturnException;
import ru.ifmo.quant.exceptions.NullCommandArgumentException;

import java.util.List;

/**
 * Created by andrey on 07.12.2016.
 */
@Component
@Scope("prototype")
public class ChooseTaskCommand extends QuantCommand {

    private List<TaskEntity> tasksList;
    private TaskEntity task;

    public String perform(QuantMessage input, HandlingProcess handlingProcess) {
        StringBuilder stringBuilder = new StringBuilder();
        if (!isInit()) {
            tasksList = dataService.findTaskEntity(handlingProcess.getAccountEntity());
            if (!tasksList.isEmpty()) {
                stringBuilder.append(ctx.getMessage("command.edittask.intro", null, input.getLocale())+"\n");
                int i = 0;
                for (TaskEntity task: tasksList) {
                    i++;
                    stringBuilder.append(i+") "+task.toString()+"\n");
                }
            } else {
                stringBuilder.append(ctx.getMessage("command.edittask.notasks", null, input.getLocale()));
                handlingProcess.clearParameters();
                handlingProcess.changeState(HandlingState.DEFAULT);
            }
            init();
        } else {
            if (task == null) {
                int i = Integer.parseInt(input.getText()) - 1;
                try {
                    task = tasksList.get(i);
                } catch (IndexOutOfBoundsException e) {
                    e.printStackTrace();
                }
                if (task != null) {
                    handlingProcess.setParameter(HandlingProcess.TASK, task);
                    if (isAfterState()) {
                        handlingProcess.changeState(getAfterState());
                        try {
                            stringBuilder.append(handlingProcess.getHandlingState().getCommandExtractor().extract(input).perform(input, handlingProcess));
                        } catch (BadCommandReturnException e) {
                            e.printStackTrace();
                        } catch (NullCommandArgumentException e) {
                            e.printStackTrace();
                        }
                    } else {
                        stringBuilder.append(ctx.getMessage("command.edittask.change", null, input.getLocale()));
                        handlingProcess.setParameter(HandlingProcess.TASK, task);
                        handlingProcess.changeState(HandlingState.CHOOSE_TASK_PARAMETER);
                    }
                } else {
                    stringBuilder.append(ctx.getMessage("command.edittask.outoftasksindex", null, input.getLocale()));
                }
            }
        }
        return stringBuilder.toString();
    }

}
