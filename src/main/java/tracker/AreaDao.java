package tracker;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class AreaDao {
    private EntityManagerFactory emf;

    public AreaDao(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public void saveArea(Area a) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(a);
        em.getTransaction().commit();
        em.close();
    }

    public void saveCityToArea(City c, String name) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Area a = findAreaByName(name);
       // a.getCities().put(c.getCityName(), c);
        c.setAreaC(a);
        //em.persist(a);
        em.persist(c);
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

//    public void addCity(Area area, City city){
//        EntityManager em = emf.createEntityManager();
//        em.getTransaction().begin();
//        area.getCities().put(city.getCityName(), city);
//        area.addCity(city);    //kölcsönös beállítás
//        em.persist(area);
//        em.persist(city);
//        em.getTransaction().commit();
//        em.close();
//    }

    public Area findAreaWithCity(String areaName){
        EntityManager em = emf.createEntityManager();
        Area result = em.createQuery(
                "select a from Area a join fetch a.cities where a.name = :name", Area.class)
                .setParameter("name", areaName)
                .getSingleResult();
        em.close();
        return result;
    }
}
