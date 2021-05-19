package tracker;

import javax.persistence.*;

@Entity
@Table(name = "town")
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cityId;

    private String cityName;
    private int population;

    @ManyToOne
    @JoinColumn(name = "areaId")
    private Area areaC;

    public City() {
    }

    public City(String cityName, int population) {
        this.cityName = cityName;
        this.population = population;
    }


    public Area getAreaC() {
        return areaC;
    }

    public void setAreaC(Area areaC) {
        this.areaC = areaC;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }
}
