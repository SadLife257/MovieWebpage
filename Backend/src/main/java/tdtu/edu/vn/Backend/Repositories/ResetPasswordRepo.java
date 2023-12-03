package tdtu.edu.vn.Backend.Repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import tdtu.edu.vn.Backend.Models.ResetPasswordModel;

@Repository
public interface ResetPasswordRepo extends CrudRepository<ResetPasswordModel, Long> {
    ResetPasswordModel findByToken(String token);
}
