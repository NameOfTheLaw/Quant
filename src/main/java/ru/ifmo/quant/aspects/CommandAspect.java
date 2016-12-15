package ru.ifmo.quant.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import ru.ifmo.quant.HandlingProcess;
import ru.ifmo.quant.QuantMessage;
import ru.ifmo.quant.commands.extractors.CommandExtractor;
import ru.ifmo.quant.exceptions.BadCommandReturnException;
import ru.ifmo.quant.exceptions.NoSuchCommandException;
import ru.ifmo.quant.exceptions.NoSuchCommandInContextException;
import ru.ifmo.quant.exceptions.NullCommandArgumentException;

/**
 * Created by andrey on 10.12.2016.
 */
@Aspect
public class CommandAspect implements ApplicationContextAware {

    private static final String cancelCommand = "/cancel";
    private ApplicationContext ctx;

    @Around(value = "execution(* ru.ifmo.quant.commands..*.perform(..)) && args(quantMessage, handlingProcess)")
    private Object performCommand(ProceedingJoinPoint pjp, QuantMessage quantMessage, HandlingProcess handlingProcess) throws Throwable {
        if (!quantMessage.hasText()) {
            throw new NullCommandArgumentException("Commands need not null message text to perform operations");
        } else {
            Object retVal = pjp.proceed();
            if (retVal == null) throw new BadCommandReturnException("Command performing returned null");
            return retVal;
        }
    }


    @Around(value = "execution(* ru.ifmo.quant.HandlingState.extractCommand(..)) && args(quantMessage)")
    private Object extractCommand(ProceedingJoinPoint pjp, QuantMessage quantMessage) throws Throwable {
        if (quantMessage.getText().equals(cancelCommand)) {
            return ctx.getBean("cancelCommand");
        }
        Object retVal = pjp.proceed();
        if (retVal == null) {
            Object defaultRetVal = ctx.getBean("defaultExtractor", CommandExtractor.class).extract(quantMessage);
            if (defaultRetVal == null) {
                throw new NoSuchCommandException("No such command in extractor " + pjp.getTarget().getClass());
            } else {
                throw new NoSuchCommandInContextException("This command is not appropriate here: "+defaultRetVal.getClass());
            }
        }
        return retVal;
    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.ctx = applicationContext;
    }

}
