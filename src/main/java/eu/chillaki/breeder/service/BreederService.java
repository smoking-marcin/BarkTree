package eu.chillaki.breeder.service;

import eu.chillaki.address.entity.Address;
import eu.chillaki.breeder.dao.BreederDao;
import eu.chillaki.breeder.entity.Breeder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class BreederService {
    private final BreederDao breederDao;

    public void create(Breeder breeder) {
        breederDao.create(breeder);
    }
    public Breeder read(Long id) {
        return breederDao.readWithAddress(id);
    }
    public Breeder update(Breeder breeder) {
        breederDao.update(breeder);
        return breeder;
    }

    public void delete(Long id) {
        Breeder breeder = breederDao.read(id);
        breederDao.delete(breeder);
    }
}
