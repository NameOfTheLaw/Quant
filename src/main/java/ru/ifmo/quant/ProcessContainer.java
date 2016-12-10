package ru.ifmo.quant;

import ru.ifmo.quant.entities.AccountEntity;

import java.util.*;

/**
 * Created by andrey on 08.11.2016.
 */
public class ProcessContainer {

    private Map<AccountEntity,HandlingProcess> processMap = new HashMap<AccountEntity, HandlingProcess>();

    public HandlingProcess getProcess(AccountEntity accountEntity) {
        return processMap.get(accountEntity);
    }

    public void addProcess(HandlingProcess process) {
        if (process != null && process.getAccountEntity() != null) {
            processMap.put(process.getAccountEntity(),process);
        }
    }

    public void killProcess(HandlingProcess process) {
        processMap.remove(process.getAccountEntity());
    }
}
