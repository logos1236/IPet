package ru.armishev.IPet.dao;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogRepository extends CrudRepository<LogPetAction, Long> {
    //@Query(value="SELECT * FROM LOGPETACTION WHERE id = ?1 AND timestamp >= ?2 ORDER BY timestamp ASC", nativeQuery = true)
    @Query(value="SELECT * FROM LOGPETACTION ORDER BY timestamp ASC", nativeQuery = true)
    List<LogPetAction> findPetActionList(long petId, long start_timestamp);
}
