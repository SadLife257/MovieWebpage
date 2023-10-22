package tdtu.edu.vn.Backend.Repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import tdtu.edu.vn.Backend.Models.BillingModel;
import tdtu.edu.vn.Backend.Models.UsersModel;

import java.util.List;

public interface BillingRepo extends CrudRepository<BillingModel, Long> {
    List<BillingModel> findByUsers(UsersModel user);
    Iterable<BillingModel> findTop5ByOrderByIdDesc();

    @Query(value = "SELECT sum(amount) FROM BillingModel WHERE confirmed=true")
    double billingSummary();

    @Query(value = "SELECT count(b) FROM BillingModel b WHERE b.confirmed=true")
    Long billingConfirmCount();

    @Query("SELECT b from BillingModel b WHERE year(b.createdAt) = :year")
    Iterable<BillingModel> findByCreateYear(@Param("year") Integer year);
}
