package eu.chillaki.breeder.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface BreederRepository extends JpaRepository<Breeder, Long> {
    @Query(value = "select distinct name from breeder where name like %:value% ", nativeQuery = true)
    List<String> findNameMatch(@Param("value") String value);

    @Query(value = "select distinct breederId from breeder where name like %:name% ", nativeQuery = true)
    List<Long> findAllMatching(@Param("name") String name);
}