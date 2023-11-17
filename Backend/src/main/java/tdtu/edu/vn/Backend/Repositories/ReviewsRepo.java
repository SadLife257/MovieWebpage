package tdtu.edu.vn.Backend.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import tdtu.edu.vn.Backend.Models.ReviewsModel;

public interface ReviewsRepo extends JpaRepository<ReviewsModel, Long> {

}
