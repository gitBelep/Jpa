package tracker;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.time.LocalDateTime;
import java.util.List;

public class ActivityDao {
    private final EntityManagerFactory emf;

    public ActivityDao(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public void saveActivities(List<Activity> activities) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        for (Activity a : activities) {
            a.setCreatedAt(LocalDateTime.now());
            em.persist(a);
        }
        em.getTransaction().commit();
        em.close();
    }

    public Activity findActivityById(long id) {
        EntityManager em = emf.createEntityManager();
        Activity resultActivity = em.find(Activity.class, id);
        em.close();
        return resultActivity;
    }

    public void updateActivity(Activity a) {
            EntityManager em = emf.createEntityManager();
            em.getTransaction().begin();
            em.merge( a ); // Visszatérési értéke a merged entitás
            em.getTransaction().commit();
            em.close();
        }

    public List<Activity> listActivities() {
        EntityManager em = emf.createEntityManager();
        List<Activity> allActivities = em.createQuery(
                "select a from Activity a order by a.startTime", Activity.class)
                .getResultList();
        em.close();
        return allActivities;
    }

    public void updateActivityDescription(long id, String desc) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
//Nem hívhatom meg a fentebbi findActivityById-t ! Akkor nem menti a változtatást.
        Activity activityToUpdate = em.find(Activity.class, id);
        activityToUpdate.setDescr(desc);
        activityToUpdate.setUpdatedAt(LocalDateTime.now());
        em.getTransaction().commit();
        em.close();
    }

    public void addLabel(String labelToAdd, long id) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Activity actual = em.find(Activity.class, id);  //getReference helyett?
        List<String> updatedLabels = actual.getLabels();   //returns only a copy
        updatedLabels.add(labelToAdd);
        actual.setLabels(updatedLabels);
        actual.setUpdatedAt(LocalDateTime.now());
        em.merge(actual);
        em.getTransaction().commit();
        em.close();
    }

    public Activity findActivityByIdWithLabels(Long id) {
        EntityManager em = emf.createEntityManager();
        Activity actual = em.createQuery(
                "select a from Activity a join fetch a.labels where a.id = :myId", Activity.class)
                .setParameter("myId", id)
                .getSingleResult();
        em.close();
        return actual;
    }

    public void addTrackPoint(TrackPoint tr, long id) {   //writing to DB
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
//        Activity a = em.getReference(Activity.class, id);  //both is OK
        Activity a = em.find(Activity.class, id);
        tr.setTrActivity(a);
//        a.addTrackPoints(tr);      //why don't we need this?
        a.setUpdatedAt(LocalDateTime.now());
        em.persist(tr);
        em.getTransaction().commit();
        em.close();
    }

    public Activity findActivityByIdWithTrackPoints(long id) {
        EntityManager em = emf.createEntityManager();
        Activity a = em.createQuery(
                "select a from Activity a join fetch a.trackPoints where a.id = :id", Activity.class)
                .setParameter("id", id)
                .getSingleResult();
        em.close();
        return a;
    }

    public void deleteActivity(long id){
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Activity actual = em.find(Activity.class, id);
        em.remove( actual );
        em.getTransaction().commit();
        em.close();
    }

    public Activity findActivityByIdWithArea(long id){
        EntityManager em = emf.createEntityManager();
        Activity actual = em.createQuery(
                "select a from Activity a join fetch a.areas where a.id = :id", Activity.class)
                .setParameter("id", id)
                .getSingleResult();
        em.close();
        return actual;
    }

}
