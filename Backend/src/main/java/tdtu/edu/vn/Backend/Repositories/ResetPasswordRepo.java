package tdtu.edu.vn.Backend.Repositories;

import org.springframework.data.repository.CrudRepository;

import tdtu.edu.vn.Backend.Models.ResetPasswordModel;

public interface ResetPasswordRepo extends CrudRepository<ResetPasswordModel, Long> {
    ResetPasswordModel findByToken(String token);
}
