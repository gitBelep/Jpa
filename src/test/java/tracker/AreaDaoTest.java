package tracker;

import org.junit.Before;
import org.junit.Test;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import static org.junit.Assert.*;

public class AreaDaoTest {
    private AreaDao aDao;
    private ActivityDao dao;

    @Before
    public void setUp() throws Exception {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("puA");
        aDao = new AreaDao(emf);
        dao = new ActivityDao(emf);

        Activity a1 = new Activity(LocalDateTime.of(1991,1,1,10,1,0), "fut", Type.RUNNING);
        Activity a2 = new Activity(LocalDateTime.of(2001,2,2,10,1,0), "kosár", Type.BASKETBALL);
        Activity a3 = new Activity(LocalDateTime.of(2011,3,3,10,1,0), "bicó", Type.BIKING);
        Activity a4 = new Activity(LocalDateTime.of(2021,4,4,10,1,0), "TÚRA", Type.HIKING);
        a2.setLabels(new ArrayList<>(List.of("initial notation")));
        List<Activity> existingActivities = new ArrayList<>(List.of(a1, a2, a3, a4));
        dao.saveActivities(existingActivities);

        Set<Activity> activities1 = Set.of(a1,a2);
        Set<Activity> activities2 = Set.of(a3,a4);

        Area r1 = new Area("Rax", activities1);
        Area r2 = new Area("Rám", activities2);
        aDao.saveArea(r1);
        aDao.saveArea(r2);
    }

    @Test
    public void findAreaByName() {
        Area area = aDao.findAreaByName("Rám");
        //System.out.println(">>>> area id: "+ area.getAreaId() );
        assertEquals(2, area.getAreaId() );

        Set<Activity> az = area.getActivities();
        //System.out.println(">>>> size: "+ az.size());
        assertEquals(2, az.size() );
    }

}
