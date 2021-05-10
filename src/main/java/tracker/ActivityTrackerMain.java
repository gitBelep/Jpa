package tracker;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class ActivityTrackerMain {

    public static void main(String[] args) {

        Activity a1 = new Activity(LocalDateTime.of(1991,1,1,10,1,0), "fut", Type.RUNNING);
        Activity a2 = new Activity(LocalDateTime.of(2001,2,2,10,1,0), "kosár", Type.BASKETBALL);
        Activity a3 = new Activity(LocalDateTime.of(2011,3,3,10,1,0), "bicó", Type.BIKING);
        Activity a4 = new Activity(LocalDateTime.of(2021,4,4,10,1,0), "TÚRA", Type.HIKING);
        List<Activity> activities = new ArrayList<>(List.of(a1, a2, a3, a4));

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("puA");
        ActivityDao dao = new ActivityDao(emf);

        dao.saveActivities(activities);

        List<Activity> activitiesBack = dao.listActivities();

        System.out.println("Mainből egyes: "+ dao.findActivityById(activitiesBack.get(1).getId()) );

//        dao.emfClose();

    }

}
