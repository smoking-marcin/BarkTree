package eu.chillaki.litter.entity;

import eu.chillaki.address.entity.Address;
import eu.chillaki.animal.dog.entity.Dog;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = Litter.TABLE_NAME)
@NoArgsConstructor //z lombok, getery, settery i konstruktory z automatu
@Data
public class Litter {
    public static final String TABLE_NAME = "litter";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long litterId;
    private String description;
    private Date date;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "litter")
    private Set<Dog> dogSet;

    public Set<Long> getDogSetIds() {
        Set<Long> dogIds = new HashSet<>();
        Hibernate.initialize(this.getDogSet());
        for (Dog dog: dogSet) {
            dogIds.add(dog.getDogId());
        }
        return dogIds;
    }

    @Override
    public String toString() {
        return "Litter{" +
                "litterId=" + litterId +
                ", description='" + description + '\'' +
                ", date=" + date + '\'' +
                ", dogIds=" + getDogSetIds() +
                '}';
    }
}
