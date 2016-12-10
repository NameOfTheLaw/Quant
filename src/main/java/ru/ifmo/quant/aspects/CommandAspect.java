package ru.ifmo.quant.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import ru.ifmo.quant.QuantMessage;
import ru.ifmo.quant.exceptions.BadCommandReturnException;
import ru.ifmo.quant.exceptions.NullCommandArgumentException;

/**
 * Created by andrey on 10.12.2016.
 */
@Aspect
public class CommandAspect {

    @Around("execution(* ru.ifmo.quant.commands..*.perform(..))")
    private Object anyCommand(ProceedingJoinPoint pjp) throws Throwable {
        Object retVal;
        QuantMessage quantMessage = (QuantMessage) pjp.getArgs()[0];
        if (quantMessage.getText() == null || quantMessage.getText().equals("")) {
            throw new NullCommandArgumentException("Commands need not null message text to perform operations");
        } else {
            retVal = pjp.proceed();
            if (retVal == null) throw new BadCommandReturnException("Command performing returned null");
        }
        return retVal;
    }
}
