package eu.chillaki.address.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface AddressRepository extends JpaRepository<Address, Long> {
    @Query(value = "select distinct city from address where city like %:value% ", nativeQuery = true)
    List<String> findCityMatch(@Param("value") String value);

    @Query(value = "select distinct country from address where country like %:value% ", nativeQuery = true)
    List<String> findCountryMatch(@Param("value") String value);

    @Query(value = "select distinct state from address where state like %:value% ", nativeQuery = true)
    List<String> findStateMatch(@Param("value") String value);

    @Query(value = "select distinct streetName from address where streetName like %:value% ", nativeQuery = true)
    List<String> findStreetNameMatch(@Param("value") String value);

    @Query(value = "select distinct zip from address where zip like %:value% ", nativeQuery = true)
    List<String> findZipMatch(@Param("value") String value);

    @Query(value = "select distinct * from address " +
            "where city like %:city% " +
            "and country like %:country% " +
            "and houseNumber like %:houseNumber% " +
            "and state like %:state% " +
            "and streetName like %:streetName% " +
            "and zip like %:zip% ",
            nativeQuery = true)
    List<Address> findAllMatching(@Param("country") String country,
                              @Param("city") String city,
                              @Param("houseNumber") String houseNumber,
                              @Param("state") String state,
                              @Param("streetName") String streetName,
                              @Param("zip") String zip);

    @Query(value = "select distinct * from address", nativeQuery = true)
    List<Address> findAll();
}