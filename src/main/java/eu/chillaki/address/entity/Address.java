package eu.chillaki.address.entity;

import eu.chillaki.breeder.entity.Breeder;
import eu.chillaki.person.entity.Person;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = Address.TABLE_NAME)
@NoArgsConstructor
@Data
public class Address {
    public static final String TABLE_NAME = "address";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long addressId;
    private String country;
    private String zip;
    private String state;
    private String city;
    private String streetName;
    private String houseNumber;
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "addressList")
    private List<Breeder> breederList = new ArrayList<>();;

    //public Set<Long> getPersonSetIds() {
    //    Set<Long> personIds = new HashSet<>();
    //    Hibernate.initialize(this.getPersonSet());
    //    for (Person person: personSet) {
    //        personIds.add(person.getPersonId());
    //    }
    //    return personIds;
    //}

    @Override
    public String toString() {
        return "Address{" +
                "addressId=" + addressId +
                ", country='" + country + '\'' +
                ", zip='" + zip + '\'' +
                ", state='" + state + '\'' +
                ", city='" + city + '\'' +
                ", streetName='" + streetName + '\'' +
                ", houseNumber='" + houseNumber + '\''+
                '}';
    }
}
