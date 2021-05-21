package tracker;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

public class TrackPointDao {
    private EntityManagerFactory emf;

    public TrackPointDao(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public List<TrackPoint> findTrackPointWithLatOrLong(double latitude, double longitude){
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        List<TrackPoint> result = em.createQuery(
                "select t from TrackPoint t where lat = :latit OR lon = :longit", TrackPoint.class)
                .setParameter("latit", latitude)
                .setParameter("longit", longitude)
                .getResultList();
        em.close();
        return result;
    }

}
