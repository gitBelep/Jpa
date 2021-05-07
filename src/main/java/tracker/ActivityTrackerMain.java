package tracker;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.flywaydb.core.Flyway;
import org.mariadb.jdbc.MariaDbDataSource;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.sql.SQLException;

public class ActivityTrackerMain {

    public static void main(String[] args) {
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
//        Flyway flyway = Flyway.configure().dataSource(ds).load();
//        flyway.clean();
//        flyway.migrate();

        Activity a1 = new Activity(LocalDateTime.of(1991,1,1,10,1,0), "fut", Type.RUNNING);
        Activity a2 = new Activity(LocalDateTime.of(2001,2,2,10,1,0), "kosár", Type.BASKETBALL);
        Activity a3 = new Activity(LocalDateTime.of(2011,3,3,10,1,0), "bicó", Type.BIKING);
        Activity a4 = new Activity(LocalDateTime.of(2021,4,4,10,1,0), "TÚRA", Type.HIKING);
        List<Activity> activities = new ArrayList<>(List.of(a1, a2, a3, a4));

        ActivityDao dao = new ActivityDao();

        dao.saveActivities(activities);

        List<Activity> activitiesBack = dao.listActivities();

        System.out.println("Mainből egyes: "+ dao.findActivityById(activitiesBack.get(1).getId()) );

        dao.emfClose();

//        EntityManagerFactory emf = Persistence.createEntityManagerFactory("puA");
//        EntityManager em = emf.createEntityManager();

//        em.getTransaction().begin();
//        for(Activity a : activities){
//            em.persist(a);
//            System.out.println(a.getType());
//        }
//        em.getTransaction().commit();
//
//        Activity resultActivity = em.find(Activity.class, 1L);
//        System.out.println(resultActivity);
//
//        List<Activity> allActivities = em.createQuery(
//                "select a from Activity a order by a.startTime", Activity.class)
//                .getResultList();
//        System.out.println(allActivities);
//
//        em.close();
//        emf.close();
    }

}
