package tracker;

import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.*;

public class ActivityDaoTest {
    private ActivityDao dao;

    @Before
    public void setUp() throws Exception {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("puA");
        dao = new ActivityDao(emf);

        Activity a1 = new Activity(LocalDateTime.of(1991,1,1,10,1,0), "fut", Type.RUNNING);
        Activity a2 = new Activity(LocalDateTime.of(2001,2,2,10,1,0), "kosár", Type.BASKETBALL);
        Activity a3 = new Activity(LocalDateTime.of(2011,3,3,10,1,0), "bicó", Type.BIKING);
        Activity a4 = new Activity(LocalDateTime.of(2021,4,4,10,1,0), "TÚRA", Type.HIKING);
        a2.setLabels(new ArrayList<>(List.of("initial notation")));
        List<Activity> existingActivities = new ArrayList<>(List.of(a1, a2, a3, a4));
        dao.saveActivities(existingActivities);

        //hibernate will do everything
//        MariaDbDataSource ds;
//        try {
//            ds = new MariaDbDataSource();
//            ds.setUrl("jdbc:mariadb://localhost:3306/activitytracker?useUnicode=true");
//            ds.setUser("activitytracker");
//            ds.setPassword("activitytracker");
//        } catch (SQLException sq) {
//            throw new IllegalStateException("Cannot connect", sq);
//        }
//
//        Flyway flyway = Flyway.configure()
//                .locations("/db/migration/Active")
//                .dataSource(ds)
//                .load();
//        flyway.clean();
//        flyway.migrate();
    }

    @Test
    public void findActivityById() {
        Activity activityWithId2 = dao.findActivityById(2);

        assertEquals(2001, activityWithId2.getStartTime().getYear());
    }

    @Test
    public void listActivities() {
        List<Activity> activities = dao.listActivities();

        assertEquals(4, activities.size());
    }

    @Test
    public  void testUpdate(){
        dao.updateActivityDescription(2L, "Az árvíztűrő :-) fiúkkal KOSÁR");
        Activity activityWithId2 = dao.findActivityById(2);

        System.out.println("created: "+ activityWithId2.getCreatedAt());
        System.out.println("updated: "+ activityWithId2.getUpdatedAt());
        System.out.println(activityWithId2.getDesc());

        assertEquals("z árvíztűrő :-) fiúkkal KOSÁR", activityWithId2.getDesc().substring(1));
        assertEquals(LocalDate.now(), activityWithId2.getCreatedAt().toLocalDate());
       }

    @Test
    public void testLabels(){
        dao.addLabel("Szélben", 1);
        dao.addLabel("Tűző napon", 2);
        dao.addLabel("Hóban", 1);
        Activity activity1 = dao.findActivityByIdWithLabels(1);
        Activity activity2 = dao.findActivityByIdWithLabels(2);

        System.out.println("labels: "+ activity1.getLabels().toString());
        System.out.println("label: "+ activity2.getLabels().toString());
//
//        assertEquals("z árvíztűrő :-) fiúkkal KOSÁR", activityWithId2.getDesc().substring(1));
//        assertEquals(LocalDate.now(), activityWithId2.getCreatedAt().toLocalDate());



    }

}
