package tracker;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "activ")
public class Activity {

    @Id
    @TableGenerator(name = "Activity_id_generator", table = "act_id_gen",
            pkColumnName = "id_gen", valueColumnName = "id_val")
    @GeneratedValue(generator = "Activity_id_generator")
    private Long id;

    @Column(name="start_time", nullable = false)
    private LocalDateTime startTime;

    @Column(name="description", nullable = false, length = 200)
    private String descr;

    @Column(nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private Type type;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ElementCollection
    @CollectionTable(name = "a_labels", joinColumns = @JoinColumn(name = "activity_id"))
    @Column(name="label")
    private List<String> labels;

    //csak visszatöltés után lekérdezhető!
    @OneToMany(mappedBy = "trActivity", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @OrderBy("time")
    private List<TrackPoint> trackPoints = new ArrayList<>();

    //Not OK: CascadeType.REMOVE ?? Removes the whole Area, from other Activities too
    @ManyToMany(mappedBy = "activities", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @OrderBy("name")
    private Set<Area> areas = new HashSet<>();


    public Activity() {
    }

    public Activity(LocalDateTime startTime, String desc, Type type) {
        this.startTime = startTime;
        this.descr = desc;
        this.type = type;
    }


    public Set<Area> getAreas() {
        return areas;
    }

    public void setAreas(Set<Area> areas) {
        this.areas = areas;
    }

    public List<TrackPoint> getTrackPoints() {
        return new ArrayList<>( trackPoints );
    }

    public void setTrackPoints(List<TrackPoint> trackPoints) {
        this.trackPoints = trackPoints;
    }

    public List<String> getLabels() {
        return new ArrayList<>( labels );
    }

    public void setLabels(List<String> labels) {
        this.labels = labels;
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
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
