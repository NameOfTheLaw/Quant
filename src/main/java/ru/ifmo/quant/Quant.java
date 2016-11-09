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

        /*
        PrettyTimeParser ptp = new PrettyTimeParser();
        String rawBody = "Next Thursday aT    18 meeting with      Clara            ";
        //String rawBody = "Next Thursday At   meeting with      Clara            ";
        List<DateGroup> parse = ptp.parseSyntax(rawBody);
        String body = rawBody.replace(parse.get(0).getText(), "").replaceAll("\\s+", " ").replaceAll("^\\s|\\s$","");
        System.out.print(rawBody); System.out.println("|");
        System.out.println(parse.get(0).getDates());
        System.out.println(parse.get(0).getText());
        System.out.print(body); System.out.println("|");
        */

        /*
        PrettyTime pt = new PrettyTime(new Locale("ru"));
        String st = pt.format(new Date(454546l));
        System.out.println(st);
        List<Date> dates = ptp.parse("Tomorrow at 18:00 meeting with Clara");
        System.out.println(dates);
        */

        /*
        for (int i = 0; i < 5; i++) {
            AccountDao accountDao = ctx.getBean("AccountDao", AccountDao.class);
            TaskDao taskDao = ctx.getBean("TaskDao", TaskDao.class);
            AccountEntity accountEntity = new AccountEntity();
            accountEntity.setVkKey(new Random().nextLong());
            accountEntity.setTelegramKey(new Random().nextLong());
            accountEntity = accountDao.save(accountEntity);

            System.out.println(accountEntity.getId());

            TaskEntity taskEntity = new TaskEntity();
            taskEntity.setAccount(accountEntity);
            taskEntity.setBody("test_body_insert");
            taskEntity.setServerDate(new Date(4244l));
            taskEntity.setClientDate(new Date(4344l));
            taskEntity = taskDao.save(taskEntity);

            System.out.println(taskEntity.getId()+" "+taskEntity.getAccount().getId());

        }
        */
    }

}
