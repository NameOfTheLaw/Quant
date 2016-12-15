package ru.ifmo.quant;

import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andrey on 15.12.2016.
 */
public enum KeyboardEnum {

    DEFAULT_KEYBOARD(new String[][] {{"/create", "/help"},{"/edit", "/today"}}),
    CREATE_KEYBOARD(new String[][] {{"/createtask", "/createnotification"}}),
    EDIT_KEYBOARD(new String[][] {{"/edittask", "/editnotification"}}),
    CHOOSE_TASK_PARAMETER(new String[][] {{"/edittaskbody", "/edittasktime"}, {"/replacetask", "/removetask(in progress)"}}),
    CANCEL_KEYBOARD(new String[][] {{"/cancel"},{"/help"}});

    ReplyKeyboardMarkup keyboard;

    KeyboardEnum(String[][] commands) {
        List<KeyboardRow> rows = new ArrayList<KeyboardRow>();
        for (int i=0; i<commands.length; i++) {
            KeyboardRow row = new KeyboardRow();
            for (int j=0; j<commands[i].length; j++) {
                row.add(new KeyboardButton().setText(commands[i][j]));
            }
            rows.add(row);
        }
        keyboard = new ReplyKeyboardMarkup();
        keyboard.setOneTimeKeyboad(true);
        keyboard.setResizeKeyboard(true);
        keyboard.setKeyboard(rows);
    }

    public ReplyKeyboardMarkup getRows() {
        return keyboard;
    }
}
