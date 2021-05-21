package tracker;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "track_points")
public class TrackPoint {

    @Id
    @TableGenerator(name = "TrackPointsIdGenerator", table = "tr_point_id_gen",
            pkColumnName = "tr_id_gen", valueColumnName = "tr_id_val")
    @GeneratedValue(generator = "TrackPointsIdGenerator")
    private long id;

    @Column(name = "tr_time")
    private LocalDateTime time;

    private double lat;
    private double lon;

    @ManyToOne
    @JoinColumn(name = "active_id")
    private Activity trActivity;


    public TrackPoint() {
    }

    public TrackPoint(LocalDateTime time, double lat, double lon) {
        this.time = time;
        this.lat = lat;
        this.lon = lon;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public Activity getTrActivity() {
        return new Activity(trActivity.getStartTime(), trActivity.getDescr(), trActivity.getType());
    }

    public void setTrActivity(Activity trActivity) {
        this.trActivity = trActivity;
    }
}
