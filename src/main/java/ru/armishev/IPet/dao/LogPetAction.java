package ru.armishev.IPet.dao;

import org.hibernate.annotations.Filter;

import javax.persistence.*;

@Entity
@Table(name = "LOGPETACTION")
public class LogPetAction {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @Filter(name = "pet")
    private long pet_id;

    private long timestamp;
    private String message;

    protected LogPetAction() {}
    public LogPetAction(long pet_id, long timestamp, String message) {
        this.pet_id = pet_id;
        this.timestamp = timestamp;
        this.message = message;
    }

    public long getPet_id() {
        return pet_id;
    }

    public void setPet_id(int pet_id) {
        this.pet_id = pet_id;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "LogAction{" +
                "id=" + id +
                ", pet_id=" + pet_id +
                ", timestamp=" + timestamp +
                ", message='" + message + '\'' +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
