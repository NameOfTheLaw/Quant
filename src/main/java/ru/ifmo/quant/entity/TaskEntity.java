package ru.ifmo.quant.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by andrey on 04.11.2016.
 */
@Entity
//@Table(name = "TASK", schema = "S191978", catalog = "")
@Table(name = "TASK")
public class TaskEntity {
    private Long id;
    private String title;
    private String body;
    private Date clientDate;
    private Date serverDate;
    private AccountEntity account;
    private List<NotificationEntity> notifications;

    @Id
    @Column(name = "ID")
    @GenericGenerator(name = "generator", strategy = "sequence-identity", parameters = @org.hibernate.annotations.Parameter(name = "sequence", value = "TASK_SEQ"))
    @GeneratedValue(generator = "generator")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "TITLE")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Basic
    @Column(name = "BODY")
    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Basic
    @Column(name = "CLIENT_DATE")
    public Date getClientDate() {
        return clientDate;
    }

    public void setClientDate(Date clientDate) {
        this.clientDate = clientDate;
    }

    @Basic
    @Column(name = "SERVER_DATE")
    public Date getServerDate() {
        return serverDate;
    }

    public void setServerDate(Date serverDate) {
        this.serverDate = serverDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TaskEntity that = (TaskEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (title != null ? !title.equals(that.title) : that.title != null) return false;
        if (body != null ? !body.equals(that.body) : that.body != null) return false;
        if (clientDate != null ? !clientDate.equals(that.clientDate) : that.clientDate != null) return false;
        if (serverDate != null ? !serverDate.equals(that.serverDate) : that.serverDate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (body != null ? body.hashCode() : 0);
        result = 31 * result + (clientDate != null ? clientDate.hashCode() : 0);
        result = 31 * result + (serverDate != null ? serverDate.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "ACCOUNT_ID", referencedColumnName = "ID", nullable = false)
    public AccountEntity getAccount() {
        return account;
    }

    public void setAccount(AccountEntity account) {
        this.account = account;
    }

    @OneToMany(mappedBy = "task")
    public List<NotificationEntity> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<NotificationEntity> notifications) {
        this.notifications = notifications;
    }
}
