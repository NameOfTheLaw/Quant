package ru.ifmo.quant.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

/**
 * Created by andrey on 04.11.2016.
 */
@Entity
@Table(name = "ACCOUNT")
public class AccountEntity {
    private Long id;
    private Long vkKey;
    private Long telegramKey;
    private List<TaskEntity> tasks;

    @Id
    @Column(name = "ID")
    @GenericGenerator(name = "generator", strategy = "sequence-identity", parameters = @org.hibernate.annotations.Parameter(name = "sequence", value = "ACCOUNT_SEQ"))
    @GeneratedValue(generator = "generator")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "VK_KEY")
    public Long getVkKey() {
        return vkKey;
    }

    public void setVkKey(Long vkKey) {
        this.vkKey = vkKey;
    }

    @Basic
    @Column(name = "TELEGRAM_KEY")
    public Long getTelegramKey() {
        return telegramKey;
    }

    public void setTelegramKey(Long telegramKey) {
        this.telegramKey = telegramKey;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AccountEntity that = (AccountEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (vkKey != null ? !vkKey.equals(that.vkKey) : that.vkKey != null) return false;
        if (telegramKey != null ? !telegramKey.equals(that.telegramKey) : that.telegramKey != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (vkKey != null ? vkKey.hashCode() : 0);
        result = 31 * result + (telegramKey != null ? telegramKey.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "account")
    public List<TaskEntity> getTasks() {
        return tasks;
    }

    public void setTasks(List<TaskEntity> tasks) {
        this.tasks = tasks;
    }
}
