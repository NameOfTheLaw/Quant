package ru.ifmo.quant.commands;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Created by andrey on 25.11.2016.
 */
@Component
@Scope("prototype")
public class NotificationConfirmationCommand extends NotificationCreatingCommand {
}