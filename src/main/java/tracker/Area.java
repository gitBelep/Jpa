package tracker;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "areas")
public class Area {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long areaId;

    private String name;

    @ManyToMany
    private Set<Activity> activities = new HashSet<>();

    @OneToMany(mappedBy = "areaC", cascade = {CascadeType.ALL})
    @MapKey(name = "cityName")
    private Map<String, City> cities = new HashMap<>();


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

    public void addCity(City c) {
        cities.put(c.getCityName(), c);
        c.setAreaC(this);
    }

    public Map<String, City> getCities() {
        return cities;
    }

    public void setCities(Map<String, City> cities) {
        this.cities = cities;
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
