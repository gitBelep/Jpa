package tracker;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "areas")
public class Area {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long areaId;

    private String name;

    @ManyToMany  //(mappedBy = "areas")
    private Set<Activity> activities = new HashSet<>();


    public Area() {
    }

    public Area(String name, Set<Activity> activities) {
        this.name = name;
        this.activities = activities;
    }


    public void addActivity(Activity a) {
        activities.add(a);
        a.getAreas().add(this);
    }


    public long getAreaId() {
        return areaId;
    }

    public void setAreaId(long areaId) {
        this.areaId = areaId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Activity> getActivities() {
        return activities;
    }

    public void setActivities(Set<Activity> activities) {
        this.activities = activities;
    }
}
