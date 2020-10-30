package ru.armishev.IPet.dao.log;

import javax.persistence.*;

/*
Логирование жизни питомца
 */
@Entity
@Table(name = "LOGPETACTION")
public class LogPetActionDAO {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private long pet_id;

    private long timestamp;
    private String message;

    protected LogPetActionDAO() {}
    public LogPetActionDAO(long pet_id, long timestamp, String message) {
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
