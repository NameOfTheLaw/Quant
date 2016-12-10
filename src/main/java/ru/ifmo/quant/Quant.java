package ru.ifmo.quant;

import org.ocpsoft.prettytime.PrettyTime;
import org.ocpsoft.prettytime.nlp.PrettyTimeParser;
import org.ocpsoft.prettytime.nlp.parse.DateGroup;
import org.springframework.context.support.GenericXmlApplicationContext;
import ru.ifmo.quant.handlers.telegram.TelegramHandler;

import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Intializing class
 * Created by andrey on 02.11.2016.
 */
public class Quant {

    public static void main(String[] args) {
        GenericXmlApplicationContext ctx = new GenericXmlApplicationContext("app-context.xml");

        //TODO: use TDD JUnit
    }

}
