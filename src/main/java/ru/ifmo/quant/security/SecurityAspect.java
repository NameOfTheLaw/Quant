package ru.ifmo.quant.security;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import ru.ifmo.quant.HandlingProcess;
import ru.ifmo.quant.QuantMessage;

/**
 * Created by andrey on 25.12.2016.
 */
@Component
@Aspect
public class SecurityAspect {

    @Before(value = "execution(* ru.ifmo.quant.commands.secure.*.perform(..)) && args(quantMessage, handlingProcess)")
    public void checkAuthentification(QuantMessage quantMessage, HandlingProcess handlingProcess) {
        if (!handlingProcess.getAuthentication().getAuthorities().equals(QuantAuthentificationManager.ADMIN_AUTHORITIES)) {
            throw new SecurityException("Access denied. Only admins can run this command");
        }
    }
}
