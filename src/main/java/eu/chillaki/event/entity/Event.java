package eu.chillaki.event.entity;

import eu.chillaki.animal.dog.entity.Dog;
import eu.chillaki.breeder.entity.Breeder;
import eu.chillaki.litter.entity.Litter;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = Event.TABLE_NAME)
@NoArgsConstructor //z lombok, getery, settery i konstruktory z automatu
@Data
public class Event {
    public static final String TABLE_NAME = "event";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long eventId;
    private String name;
    private String description;
    private String type;
    @ManyToOne
    private Dog dog;

    @Override
    public String toString() {
        return "Event{" +
                "eventId=" + eventId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
