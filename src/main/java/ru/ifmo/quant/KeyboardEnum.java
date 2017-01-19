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

    DEFAULT(new String[][] {{"/ct", "/cn"}, {"/today", "/week", "/tasks"}, {"/edit", "/settings", "/help"}}),
    CREATE(new String[][] {{"/createtask", "/createnotification"}}),
    EDIT(new String[][] {{"/edittask", "/editnotification"}}),
    CHOOSE_TASK_PARAMETER(new String[][] {{"/edittaskbody", "/edittasktime"}, {"/replacetask", "/removetask"}}),
    CANCEL(new String[][] {{"/cancel"}}),
    ADMIN(new String[][] {{"/botstatus", "/refreshDB"},{"/cancel"}}),
    ACCOUNT_SETTINGS(new String[][] {{"/changelanguage"},{"/cancel"}}),
    CHANGE_LANGUAGE(new String[][] {{"/en", "/ru"}});

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
        //keyboard.setOneTimeKeyboad(true);
        keyboard.setResizeKeyboard(true);
        keyboard.setKeyboard(rows);
    }

    public ReplyKeyboardMarkup getRows() {
        return keyboard;
    }
}
