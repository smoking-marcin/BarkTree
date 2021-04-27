package eu.chillaki.person.entity;


import eu.chillaki.address.entity.Address;
import eu.chillaki.breeder.entity.Breeder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = Person.TABLE_NAME)
@NoArgsConstructor //z lombok, getery, settery i konstruktory z automatu
@Data
public class Person {
    public static final String TABLE_NAME = "person";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long personId;
    private String name;
    private String surname;
    @ManyToMany
    private Set<Address> addressSet;

    public Set<Long> getAddressSetIds() {
        Set<Long> addressIds = new HashSet<>();
        Hibernate.initialize(this.getAddressSet());
        for (Address address: addressSet) {
            addressIds.add(address.getAddressId());
        }
        return addressIds;
    }

    @Override
    public String toString() {
        return "Person{" +
                "personId=" + personId +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", breederIds=" + getAddressSetIds().toString() +
                '}';
    }
}
