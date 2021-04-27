package eu.chillaki.animal.dog.dao;

import eu.chillaki.address.entity.Address;
import eu.chillaki.animal.dog.entity.Dog;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Repository
@Transactional
public class DogDao {
    @PersistenceContext
    private EntityManager entityManager;

    public void create(Dog dog) {
        entityManager.persist(dog);
    }
    public Dog read(long id) {
        return entityManager.find(Dog.class, id);
    }
    public void update(Dog dog) {
        entityManager.merge(dog);
    }
    public void delete(Dog dog) {
        entityManager.remove(entityManager.contains(dog) ? dog : entityManager.merge(dog));
    }
}
