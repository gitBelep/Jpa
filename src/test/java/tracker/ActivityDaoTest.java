package tracker;

import org.junit.Before;
import org.junit.Test;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

public class ActivityDaoTest {
    private ActivityDao dao;
    private TrackPointDao tpDao;
    private AreaDao areaDao;

    @Before
    public void setUp() throws Exception {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("puA");
        dao = new ActivityDao(emf);
        tpDao = new TrackPointDao(emf);
        areaDao = new AreaDao(emf);

        Activity a1 = new Activity(LocalDateTime.of(1991,1,1,10,1,0), "fut", Type.RUNNING);
        Activity a2 = new Activity(LocalDateTime.of(2001,2,2,10,1,0), "kosár", Type.BASKETBALL);
        Activity a3 = new Activity(LocalDateTime.of(2011,3,3,10,1,0), "bicó", Type.BIKING);
        Activity a4 = new Activity(LocalDateTime.of(2021,4,4,10,1,0), "TÚRA", Type.HIKING);
        a2.setLabels(new ArrayList<>(List.of("initial notation")));
        List<Activity> existingActivities = new ArrayList<>(List.of(a1, a2, a3, a4));
        dao.saveActivities(existingActivities);
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

        assertEquals( List.of("Szélben", "Hóban"), activity1.getLabels());
        assertEquals( List.of("initial notation", "Tűző napon"), activity2.getLabels());
    }

    @Test
    public void testTrackingPoint() {
        TrackPoint tp1 = new TrackPoint(LocalDateTime.of(2019, 12, 12, 12, 12, 12), 7.123, 8.456);
        TrackPoint tp2 = new TrackPoint(LocalDateTime.of(2019, 12, 12, 12, 50, 12), 7.2, 8.5);
        TrackPoint tp3 = new TrackPoint(LocalDateTime.of(2019, 12, 12, 13, 55, 12), 7.3, 8.6);
        TrackPoint tp4 = new TrackPoint(LocalDateTime.of(2019, 11, 12, 14, 12, 12), 7.123, 8.456);
        TrackPoint tp5 = new TrackPoint(LocalDateTime.of(2019, 12, 12, 12, 50, 12), 7.2, 8.5);
        TrackPoint tp6 = new TrackPoint(LocalDateTime.of(2019, 12, 11, 12, 50, 12), 7.3, 8.6);

        Activity a5pre = new Activity(LocalDateTime.of(2019, 12, 12, 11, 59, 0), "TÚRA", Type.HIKING);
        a5pre.setLabels(new ArrayList<>(List.of("meredek")));

        dao.saveActivities(List.of(a5pre));
        Activity a5 = dao.findActivityById(5L);

        dao.addTrackPoint(tp3, a5.getId());
        dao.addTrackPoint(tp2, a5.getId());
        dao.addTrackPoint(tp6, 4);
        dao.addTrackPoint(tp4, 4);
        dao.addTrackPoint(tp5, 4);
        dao.addTrackPoint(tp1, a5.getId());

        Activity a5WithTp = dao.findActivityByIdWithTrackPoints(a5.getId());
        System.out.println(">>>> Tp time1: " + a5WithTp.getTrackPoints().get(0).getTime());
        System.out.println(">>>> Tp time2: " + a5WithTp.getTrackPoints().get(1).getTime());
        assertEquals(LocalDateTime.of(2019, 12, 12, 13, 55, 12),
                a5WithTp.getTrackPoints().get(2).getTime());
        assertEquals(3, a5WithTp.getTrackPoints().size());

        Activity a4WithTp = dao.findActivityByIdWithTrackPoints(4);
        System.out.println(">>>> Tp time1: " + a4WithTp.getTrackPoints().get(0).getTime());
        System.out.println(">>>> Tp time2: " + a4WithTp.getTrackPoints().get(1).getTime());
        System.out.println(">>>> Tp time3: " + a4WithTp.getTrackPoints().get(2).getTime());
        assertEquals(LocalDateTime.of(2019, 11, 12, 14, 12, 12),
                a4WithTp.getTrackPoints().get(0).getTime());
        assertEquals(3, a4WithTp.getTrackPoints().size());
    }

    @Test
    public void testTrackingSearchPointWithLatOrLong(){
        TrackPoint tp1 = new TrackPoint(LocalDateTime.of(2019,12,12,12,12,12), 7.123, 8.456);
        TrackPoint tp2 = new TrackPoint(LocalDateTime.of(2019,12,12,12,50,12), 7.2, 8.5);
        TrackPoint tp3 = new TrackPoint(LocalDateTime.of(2019,12,12,13,55,12), 7.3, 8.6);
        TrackPoint tp4 = new TrackPoint(LocalDateTime.of(2019,11,12,14,12,12), 7.123, 8.456);
        TrackPoint tp5 = new TrackPoint(LocalDateTime.of(2019,12,12,12,50,12), 7.2, 8.5);
        TrackPoint tp6 = new TrackPoint(LocalDateTime.of(2019,12,11,12,50,12), 7.3, 8.6);

        dao.addTrackPoint(tp3, 3);
        dao.addTrackPoint(tp2, 3);
        dao.addTrackPoint(tp6, 4);
        dao.addTrackPoint(tp4, 4);
        dao.addTrackPoint(tp5, 4);
        dao.addTrackPoint(tp1, 3);

        List<TrackPoint> tpLat = tpDao.findTrackPointWithLatOrLong(7.2, 77);
        System.out.println(">>>> 0: "+ tpLat.get(0).getTime());
        assertEquals(2, tpLat.size());

        List<TrackPoint> tpLon = tpDao.findTrackPointWithLatOrLong(77, 8.6);
        System.out.println("\">>>> "+ tpLon.size() +" db,0: "+ tpLon.get(0).getTime());
        assertEquals(2, tpLon.size());
    }

    @Test
    public void testActivitiesWithAreaThenCascadeDelete(){
        TrackPoint tp = new TrackPoint(LocalDateTime.of(2019,12,12,12,12,44), 7.123, 8.456);
        dao.addTrackPoint(tp, 2);
        Activity act2WithTr = dao.findActivityByIdWithTrackPoints(2);
        //System.out.println(">>> time: "+ act2WithTr.getTrackPoints().get(0).getTime());
        assertEquals(44, act2WithTr.getTrackPoints().get(0).getTime().getSecond());

        Activity act1 = dao.findActivityById(1);

        Set<Activity> activities = Set.of(act2WithTr, act1);
        Area r1 = new Area("Rax-Alpen", activities);
        areaDao.saveArea(r1);
        Activity acti2WithArea = dao.findActivityByIdWithArea(2);
        Activity acti1WithArea = dao.findActivityByIdWithArea(1);

        assertEquals(1, acti2WithArea.getAreas().size());

        dao.deleteActivity(2);
        Activity nonExistingActivity = dao.findActivityById(2);
        assertEquals(null, nonExistingActivity);

        //acti1WithArea has the Area (OK), but Area disappeared from DB (not OK)
        assertEquals(1, acti1WithArea.getAreas().size());
    }


}
