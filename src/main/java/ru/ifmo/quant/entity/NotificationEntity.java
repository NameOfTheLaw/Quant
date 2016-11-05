package ru.ifmo.quant.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by andrey on 04.11.2016.
 */
@Entity
//@Table(name = "NOTIFICATION", schema = "S191978", catalog = "")
@Table(name = "NOTIFICATION")
public class NotificationEntity {
    private Long id;
    private Date clientDate;
    private Date serverDate;
    private TaskEntity task;

    @Id
    @Column(name = "ID")
    @GenericGenerator(name = "generator", strategy = "sequence-identity", parameters = @org.hibernate.annotations.Parameter(name = "sequence", value = "NOTIFICATION_SEQ"))
    @GeneratedValue(generator = "generator")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

        NotificationEntity that = (NotificationEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (clientDate != null ? !clientDate.equals(that.clientDate) : that.clientDate != null) return false;
        if (serverDate != null ? !serverDate.equals(that.serverDate) : that.serverDate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (clientDate != null ? clientDate.hashCode() : 0);
        result = 31 * result + (serverDate != null ? serverDate.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "TASK_ID", referencedColumnName = "ID", nullable = false)
    public TaskEntity getTask() {
        return task;
    }

    public void setTask(TaskEntity task) {
        this.task = task;
    }
}
