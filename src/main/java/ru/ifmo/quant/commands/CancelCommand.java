package ru.ifmo.quant.commands;

import org.springframework.beans.factory.annotation.Autowired;
import ru.ifmo.quant.HandlingProcess;
import ru.ifmo.quant.ProcessContainer;
import ru.ifmo.quant.QuantMessage;
import ru.ifmo.quant.dao.DataService;
import ru.ifmo.quant.entity.AccountEntity;
import ru.ifmo.quant.exceptions.WrongContextCommandException;

/**
 * Created by andrey on 22.11.2016.
 */
public class CancelCommand implements QuantCommand {

    public String perform(QuantMessage input, AccountEntity account, HandlingProcess process, DataService dataService) throws WrongContextCommandException {
        process.getProcessContainer().killProcess(process);
        String answer = "Okay. Nothing happend";
        return answer;
    }
}
