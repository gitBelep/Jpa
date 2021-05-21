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
        //a.getCities().put(c.getCityName(), c);
        //em.persist(a);  -> update .merge(a); ??
        c.setAreaC(a);
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

    public Area findAreaWithCity(String areaName){
        EntityManager em = emf.createEntityManager();
        Area result = em.createQuery(
                "select a from Area a join fetch a.cities where a.name = :name", Area.class)
                .setParameter("name", areaName)
                .getSingleResult();
        em.close();
        return result;
    }

    public City findCity(Integer id){           //contains Area!
        // in A: @OneToMany(mappedBy = "areaC", cascade = {CascadeType.ALL})
        EntityManager em = emf.createEntityManager();
        City result = em.find(City.class, id);
        em.close();
        return result;
    }

    public City findCityByName(String name){     //contains Area
        EntityManager em = emf.createEntityManager();
        City result = em.createQuery(
                "select c from City c where c.cityName = :n", City.class)
                .setParameter("n", name)
                .getSingleResult();
        em.close();
        return result;
    }

}
