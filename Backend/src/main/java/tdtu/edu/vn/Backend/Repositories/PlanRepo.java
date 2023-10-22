package tdtu.edu.vn.Backend.Repositories;

import org.springframework.data.repository.CrudRepository;

import tdtu.edu.vn.Backend.Models.PlanModel;

public interface PlanRepo extends CrudRepository<PlanModel, Long> {
}
