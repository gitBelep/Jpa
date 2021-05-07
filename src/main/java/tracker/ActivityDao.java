package tracker;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class ActivityDao {
    private EntityManagerFactory emf;

    public ActivityDao() {
        emf = Persistence.createEntityManagerFactory("puA");
    }

    public void saveActivities(List<Activity> activities){
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        for(Activity a : activities){
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

    public void emfClose(){
        emf.close();
    }

}
