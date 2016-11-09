package ru.ifmo.quant.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ifmo.quant.dao.account.AccountDao;
import ru.ifmo.quant.dao.notification.NotificationDao;
import ru.ifmo.quant.dao.task.TaskDao;

/**
 * Created by andrey on 08.11.2016.
 */
public class QuantDao {
    private AccountDao accountDao;
    private NotificationDao notificationDao;
    private TaskDao taskDao;

    public AccountDao getAccountDao() {
        return accountDao;
    }

    public void setAccountDao(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    public NotificationDao getNotificationDao() {
        return notificationDao;
    }

    public void setNotificationDao(NotificationDao notificationDao) {
        this.notificationDao = notificationDao;
    }

    public TaskDao getTaskDao() {
        return taskDao;
    }

    public void setTaskDao(TaskDao taskDao) {
        this.taskDao = taskDao;
    }
}
