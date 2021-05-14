package tracker;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class AreaDao {
    private EntityManagerFactory emf;

    public AreaDao(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public void saveArea(Area a){
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist( a );
        em.getTransaction().commit();
        em.close();
    }

    public Area findAreaByName(String myName){
        EntityManager em = emf.createEntityManager();
        Area actual = em.createQuery(
                "select a from Area a join fetch a.activities where a.name = :aName", Area.class)
                .setParameter("aName", myName)
                .getSingleResult();
        em.close();
        return actual;
    }

}
