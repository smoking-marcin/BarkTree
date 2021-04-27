package eu.chillaki.animal.dog.service;

import eu.chillaki.address.dao.AddressDao;
import eu.chillaki.address.entity.Address;
import eu.chillaki.animal.dog.dao.DogDao;
import eu.chillaki.animal.dog.entity.Dog;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class DogService {
    private final DogDao dogDao;

    public void create(Dog dog) {dogDao.create(dog);
    }
    public Dog read(Long id) {
        return dogDao.read(id);
    }

    public Dog update(Dog dog) {
        dogDao.update(dog);
        return dog;
    }

    public void delete(Long id) {
        Dog dog = dogDao.read(id);
        dogDao.delete(dog);
    }
}
