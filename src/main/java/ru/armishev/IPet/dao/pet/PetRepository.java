package ru.armishev.IPet.dao.pet;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PetRepository extends CrudRepository<PetDAO, Long> {
}
