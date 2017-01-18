package ru.ifmo.quant.entities;

import ru.ifmo.quant.DateExtractor;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.List;
import java.sql.Timestamp;

/**
 * Created by andrey on 04.11.2016.
 */
@Entity
@Table(name = "TASK")
public class TaskEntity {

    public final static int SPECIFIED_TIME = 1;
    public final static int NO_SPECIFIED_TIME = 0;

    private Long id;
    private String title;
    private String body;
    private Timestamp date;
    private int specifiedTime;
    private AccountEntity account;
    private List<NotificationEntity> notifications;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "generator", sequenceName = "TASK_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generator")
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
    @Column(name = "SERVER_DATE")
    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    @Basic
    @Column(name = "SPECIFIED_TIME")
    public int getSpecifiedTime() {
        return specifiedTime;
    }

    public void setSpecifiedTime(int specifiedTime) {
        this.specifiedTime = specifiedTime;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TaskEntity that = (TaskEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        //if (title != null ? !title.equals(that.title) : that.title != null) return false;
        //if (body != null ? !body.equals(that.body) : that.body != null) return false;
        //if (serverDate != null ? !serverDate.equals(that.serverDate) : that.serverDate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        //result = 31 * result + (title != null ? title.hashCode() : 0);
        //result = 31 * result + (body != null ? body.hashCode() : 0);
        //result = 31 * result + (serverTime != null ? serverTime.hashCode() : 0);
        //result = 31 * result + (serverDate != null ? serverDate.hashCode() : 0);
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

    @OneToMany(mappedBy = "task", orphanRemoval = true)
    public List<NotificationEntity> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<NotificationEntity> notifications) {
        this.notifications = notifications;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(getBody()+"\n");
        SimpleDateFormat sdf;
        if (specifiedTime == SPECIFIED_TIME) {
            sdf = new SimpleDateFormat("dd.MM.yy HH:mm");
        } else {
            sdf = new SimpleDateFormat("dd.MM.yy");
        }

        stringBuilder.append("_"+sdf.format(getDate())+"_");
        return stringBuilder.toString();
    }

    public void extractDate(DateExtractor dateExtractor) {
        setDate(dateExtractor.getDate());
        if (dateExtractor.isSpecifiedTime()) {
            setSpecifiedTime(this.SPECIFIED_TIME);
        } else {
            setSpecifiedTime(this.NO_SPECIFIED_TIME);
        }
    }
}
