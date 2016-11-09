package ru.ifmo.quant;

import ru.ifmo.quant.entity.AccountEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by andrey on 08.11.2016.
 */
public class ProcessContainer {

    private List<HandlingProcess> processes = new ArrayList<HandlingProcess>();

    public HandlingProcess getProcess(AccountEntity accountEntity) {
        if (accountEntity != null && processes.contains(accountEntity)) {
            int index = processes.indexOf(accountEntity);
            return processes.get(index);
        }
        return null;
    };

    public void addProcess(HandlingProcess process) {
        if (process != null) {
            processes.add(process);
        }
    };

    public void killProcess(HandlingProcess process) {
        processes.remove(process);
    }
}
