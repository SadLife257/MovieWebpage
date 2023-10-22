package tdtu.edu.vn.Backend.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import tdtu.edu.vn.Backend.Models.UsersModel;

@Repository
public interface UsersRepo extends CrudRepository<UsersModel, Long> {

    UsersModel findByUsername(String username);

//  UsersModel findByRefreshToken(String refreshToken);

    Iterable<UsersModel> findTop5ByOrderByIdDesc();
}
