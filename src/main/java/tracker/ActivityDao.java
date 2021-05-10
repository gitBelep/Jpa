package tracker;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ActivityDao {
    private EntityManagerFactory emf;

    public ActivityDao(EntityManagerFactory emf) {
        this.emf = emf;   // = Persistence.createEntityManagerFactory("puA");
    }

    public void saveActivities(List<Activity> activities){
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        for(Activity a : activities){
            a.setCreatedAt(LocalDateTime.now());
            em.persist(a);
        }
        em.getTransaction().commit();
        em.close();
    }

    public Activity findActivityById(long id){
        EntityManager em = emf.createEntityManager();
        Activity resultActivity = em.find(Activity.class, id);
        em.close();
        return resultActivity;
    }

    public List<Activity> listActivities() {
        EntityManager em = emf.createEntityManager();
        List<Activity> allActivities = em.createQuery(
                "select a from Activity a order by a.startTime", Activity.class)
                .getResultList();
        em.close();
        return allActivities;
    }

    public void updateActivityDescription(long id, String desc){
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
//Nem hívhatom meg a fentebbi findActivityById-t ! Akkor nem menti a változtatást.
        Activity activityToUpdate =em.find(Activity.class, id);
        activityToUpdate.setDesc(desc);
        activityToUpdate.setUpdatedAt(LocalDateTime.now());
        em.getTransaction().commit();
        em.close();
    }

    public void addLabel(String labelToAdd, long id){
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Activity actual = em.getReference(Activity.class, id);
        List<String> updatedLabels = actual.getLabels();   //returns only a copy
        updatedLabels.add( labelToAdd );
        actual.setLabels( updatedLabels );
        actual.setUpdatedAt( LocalDateTime.now() );
        em.persist(actual);
        em.getTransaction().commit();
        em.close();
    }

    public Activity findActivityByIdWithLabels(long id){
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Activity actual = em.createQuery(
                "select a from Activity a join fetch a.labels where id = :myId", Activity.class)
                .setParameter("myId", id)
                .getSingleResult();
        em.close();
        return actual;
    }

//we dont need main() any more
//    public void emfClose(){
//        emf.close();
//    }

}
