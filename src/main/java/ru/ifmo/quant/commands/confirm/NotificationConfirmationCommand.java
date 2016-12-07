package ru.ifmo.quant.commands.confirm;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.ifmo.quant.commands.create.NotificationCreatingCommand;

/**
 * Created by andrey on 25.11.2016.
 */
@Component
@Scope("prototype")
public class NotificationConfirmationCommand extends NotificationCreatingCommand {
}