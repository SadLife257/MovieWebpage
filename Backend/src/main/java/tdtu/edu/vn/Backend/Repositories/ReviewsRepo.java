package tdtu.edu.vn.Backend.Repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import tdtu.edu.vn.Backend.Models.ReviewsModel;

@Repository
public interface ReviewsRepo extends CrudRepository<ReviewsModel, Long> {

}
