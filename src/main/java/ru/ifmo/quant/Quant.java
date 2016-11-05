package ru.ifmo.quant;

import org.springframework.context.support.GenericXmlApplicationContext;
import ru.ifmo.quant.dao.account.AccountDao;
import ru.ifmo.quant.dao.task.TaskDao;
import ru.ifmo.quant.entity.AccountEntity;
import ru.ifmo.quant.entity.TaskEntity;

import java.util.Date;
import java.util.Random;

/**
 * Intializing class
 * Created by andrey on 02.11.2016.
 */
public class Quant {

    public static void main(String[] args) {
        GenericXmlApplicationContext ctx = new GenericXmlApplicationContext("app-context.xml");
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

}
