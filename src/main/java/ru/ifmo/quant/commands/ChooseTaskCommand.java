package ru.ifmo.quant.commands;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.ifmo.quant.*;
import ru.ifmo.quant.entities.TaskEntity;
import ru.ifmo.quant.exceptions.BadCommandReturnException;
import ru.ifmo.quant.exceptions.NullCommandArgumentException;

import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * Created by andrey on 07.12.2016.
 */
@Component
@Scope("prototype")
public class ChooseTaskCommand extends QuantCommand {

    private List<TaskEntity> tasksList;
    private TaskEntity task;

    public Queue<QuantMessage> perform(QuantMessage input, HandlingProcess handlingProcess) {
        StringBuilder stringBuilder = new StringBuilder();
        Queue<QuantMessage> output = new LinkedList<QuantMessage>();
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
                            output.addAll(handlingProcess.getHandlingState().getCommandExtractor().extract(input).perform(input, handlingProcess));
                        } catch (BadCommandReturnException e) {
                            e.printStackTrace();
                        } catch (NullCommandArgumentException e) {
                            e.printStackTrace();
                        }
                    } else {
                        stringBuilder.append(ctx.getMessage("command.edittask.change", null, input.getLocale()));
                        output.add(new OutputMessage(input, stringBuilder.toString()).setKeyboard(KeyboardEnum.CHOOSE_TASK_PARAMETER));
                        handlingProcess.setParameter(HandlingProcess.TASK, task);
                        handlingProcess.changeState(HandlingState.CHOOSE_TASK_PARAMETER);
                        return output;
                    }
                } else {
                    stringBuilder.append(ctx.getMessage("command.edittask.outoftasksindex", null, input.getLocale()));
                }
            }
        }
        QuantMessage answer = new OutputMessage(input, stringBuilder.toString());
        output.add(answer);
        return output;
    }

}
