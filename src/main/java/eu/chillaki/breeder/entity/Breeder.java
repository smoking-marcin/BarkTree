package eu.chillaki.breeder.entity;

import eu.chillaki.address.entity.Address;
import eu.chillaki.address.entity.AddressRepository;
import eu.chillaki.litter.entity.Litter;
import eu.chillaki.person.entity.Person;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = Breeder.TABLE_NAME)
@NoArgsConstructor
@Data
public class Breeder {
    public static final String TABLE_NAME = "breeder";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long breederId;
    @Column(unique = true)
    private String name;
    @ManyToMany
    private List<Address> addressList = new ArrayList<>();
    //@ManyToMany
    //private Set<Person> personSet;
    //@OneToMany
    //private Set<Litter> litterSet;


    public List<Address> getAddressList() {
        return addressList;
    }

    public void addAddress(Address address) {
        addressList.add(address);
    }

    @Override
    public String toString() {
        if (this.addressList.size() == 0) {
            return "Breeder{breederId=" + breederId +", name='" + name + '}';
        } else {
            return "Breeder{breederId=" + breederId +", name='" + name +", addressList=" + addressList +'}';
        }

    }

}
