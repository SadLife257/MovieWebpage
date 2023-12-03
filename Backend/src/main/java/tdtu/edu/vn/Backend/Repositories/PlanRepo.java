package tdtu.edu.vn.Backend.Repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import tdtu.edu.vn.Backend.Models.PlanModel;

@Repository
public interface PlanRepo extends CrudRepository<PlanModel, Long> {
}
