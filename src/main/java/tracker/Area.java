package tracker;

import javax.persistence.*;

@Entity
@Table(name = "areas")
public class Area {

    @Id
    @TableGenerator(name = "Area_id_generator", table = "area_id_gen",
            pkColumnName = "id_gen", valueColumnName = "id_val")
    @GeneratedValue(generator = "Area_id_generator")
    private long areaId;

    private String name;


}
