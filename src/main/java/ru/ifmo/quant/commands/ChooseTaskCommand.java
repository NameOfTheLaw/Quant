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
                stringBuilder.append(ctx.getMessage("command.edittask.intro", null, handlingProcess.getAccountEntity().LOCALE)+"\n");
                int i = 0;
                for (TaskEntity task: tasksList) {
                    i++;
                    stringBuilder.append(i+") "+task.toString()+"\n");
                }
                output.add(new OutputMessage(input, stringBuilder.toString())
                    .setKeyboard(KeyboardEnum.CANCEL));
                if (input.getText().equals(CN_COMMAND)) {
                    handlingProcess.clearParameters();
                    handlingProcess.changeState(HandlingState.CREATE);
                    handlingProcess.getHandlingState().getCommandExtractor().setExecutingCommand(this);
                    setAfterState(HandlingState.NOTIFICATION_CREATE);
                }
                init();
                return output;
            } else {
                stringBuilder.append(ctx.getMessage("command.edittask.notasks", null, handlingProcess.getAccountEntity().LOCALE));
                handlingProcess.clearParameters();
                handlingProcess.changeState(HandlingState.DEFAULT);
            }
        } else {
            if (task == null) {
                try {
                    int i = Integer.parseInt(input.getText()) - 1;
                    task = tasksList.get(i);
                } catch (Exception e) {
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
                        return output;
                    } else {
                        stringBuilder.append(ctx.getMessage("command.edittask.change", null, handlingProcess.getAccountEntity().LOCALE));
                        handlingProcess.changeState(HandlingState.CHOOSE_TASK_PARAMETER);
                        output.add(new OutputMessage(input, stringBuilder.toString())
                            .setKeyboard(KeyboardEnum.CHOOSE_TASK_PARAMETER));
                        return output;
                    }
                } else {
                    stringBuilder.append(ctx.getMessage("command.edittask.outoftasksindex", null, handlingProcess.getAccountEntity().LOCALE));
                }
            }
        }
        QuantMessage answer = new OutputMessage(input, stringBuilder.toString());
        output.add(answer);
        return output;
    }

}
