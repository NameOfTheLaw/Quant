package ru.ifmo.quant.entities;

import ru.ifmo.quant.ExtractedDate;

import javax.persistence.*;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by andrey on 04.11.2016.
 */
@Entity
@Table(name = "NOTIFICATION")
public class NotificationEntity {

    public final static int SPECIFIED_TIME = 1;
    public final static int NO_SPECIFIED_TIME = 0;

    private Long id;
    private Date date;
    private int specifiedTime;
    private TaskEntity task;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "generator", sequenceName = "NOTIFICATION_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generator")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "SERVER_DATE")
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
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

        NotificationEntity that = (NotificationEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        //if (serverTime != null ? !serverTime.equals(that.serverTime) : that.serverTime != null) return false;
        //if (serverDate != null ? !serverDate.equals(that.serverDate) : that.serverDate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        //result = 31 * result + (serverTime != null ? serverTime.hashCode() : 0);
        //result = 31 * result + (serverDate != null ? serverDate.hashCode() : 0);
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

    public void loadDate(ExtractedDate extractedDate) {
        setDate(extractedDate.getServerDate());
        if (extractedDate.isSpecifiedTime()) {
            setSpecifiedTime(this.SPECIFIED_TIME);
        } else {
            setSpecifiedTime(this.NO_SPECIFIED_TIME);
        }
    }

    @Override
    public String toString() {
        SimpleDateFormat sdf;
        if (specifiedTime == SPECIFIED_TIME) {
            sdf = new SimpleDateFormat("dd.MM.yy HH:mm");
        } else {
            sdf = new SimpleDateFormat("dd.MM.yy");
        }
        return "_"+sdf.format(getDate())+"_";
    }
}
