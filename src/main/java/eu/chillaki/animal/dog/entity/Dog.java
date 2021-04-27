package eu.chillaki.animal.dog.entity;

import eu.chillaki.breeder.entity.Breeder;
import eu.chillaki.event.entity.Event;
import eu.chillaki.litter.entity.Litter;
import eu.chillaki.person.entity.Person;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = Dog.TABLE_NAME)
@NoArgsConstructor //z lombok, getery, settery i konstruktory z automatu
@Data
public class Dog {
    public static final String TABLE_NAME = "dog";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long dogId;
    private String name;
    @ManyToOne
    private Litter litter;
    @ManyToOne
    private Breeder breeder;
    @OneToMany(mappedBy="dog")
    private List<Event> eventList;

    @Override
    public String toString() {
        return "Dog{" +
                "dogId=" + dogId +
                ", name='" + name + '\'' +
                ", litter=" + litter +
                ", breeder=" + breeder +
                '}';
    }
}
