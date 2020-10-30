package ru.armishev.IPet.dao.log;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogRepository extends CrudRepository<LogPetActionDAO, Long> {
    @Query(value="SELECT * FROM LOGPETACTION WHERE pet_id = ?1 AND timestamp >= ?2 ORDER BY timestamp DESC", nativeQuery = true)
    List<LogPetActionDAO> findPetActionList(long petId, long start_timestamp);
}
