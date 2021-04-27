package eu.chillaki.address.dao;

import eu.chillaki.address.entity.Address;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Repository
@Transactional
public class AddressDao {
    @PersistenceContext
    private EntityManager entityManager;
    
    public void create(Address address) {
        entityManager.persist(address);
    }
    public Address read(long id) {
        return entityManager.find(Address.class, id);
    }
    public void update(Address address) {
        entityManager.merge(address);
    }
    public void delete(Address address) {
        entityManager.remove(entityManager.contains(address) ? address : entityManager.merge(address));
    }
}
