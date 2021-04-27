package eu.chillaki.address.service;

import eu.chillaki.address.dao.AddressDao;
import eu.chillaki.address.entity.Address;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AddressService {
    private final AddressDao addressDao;

    public void create(Address address) {
        addressDao.create(address);
    }
    public Address read(Long id) {
        return addressDao.read(id);
    }

    public Address update(Address address) {
        addressDao.update(address);
        return address;
    }

    public void delete(Long id) {
        Address address = addressDao.read(id);
        addressDao.delete(address);
    }
}
