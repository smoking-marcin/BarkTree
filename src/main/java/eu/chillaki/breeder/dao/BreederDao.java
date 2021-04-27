package eu.chillaki.breeder.dao;

import eu.chillaki.address.entity.Address;
import eu.chillaki.breeder.entity.Breeder;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class BreederDao {
    @PersistenceContext
    private EntityManager entityManager;

    public void create(Breeder breeder) {
        entityManager.persist(breeder);
    }
    public Breeder read(Long id) {
        return entityManager.find(Breeder.class, id);
    }
    public void update(Breeder breeder) {
        entityManager.merge(breeder);
    }
    public void delete(Breeder breeder) {
        entityManager.remove(entityManager.contains(breeder) ? breeder : entityManager.merge(breeder));
    }

    public Breeder readWithAddress(Long id) {
        Breeder breeder = read(id);
        Hibernate.initialize(breeder.getAddressList());
        return breeder;
    }

}
