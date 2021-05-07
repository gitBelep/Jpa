package tracker;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "activ")
public class Activity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="start_time", nullable = false)
    private LocalDateTime startTime;

    @Column(name="description", nullable = false, length = 200)
    private String descr;

    @Column(nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private Type type;

    public Activity() {
    }

    public Activity(LocalDateTime startTime, String desc, Type type) {
        this.startTime = startTime;
        this.descr = desc;
        this.type = type;
    }

    public long getId() {
        return id;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public String getDesc() {
        return descr;
    }

    public Type getType() {
        return type;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public void setDesc(String desc) {
        this.descr = desc;
    }

    public void setType(Type type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return id +" "+ startTime +" "+ descr +" "+ type.name();
    }
}
