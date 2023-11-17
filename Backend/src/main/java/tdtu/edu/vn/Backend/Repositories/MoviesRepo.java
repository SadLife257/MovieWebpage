package tdtu.edu.vn.Backend.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import tdtu.edu.vn.Backend.Models.MoviesModel;

public interface MoviesRepo extends JpaRepository<MoviesModel, Long> {
    Iterable<MoviesModel> findByActiveTrue();
    Iterable<MoviesModel> findByActiveFalse();
    Iterable<MoviesModel> findTop5ByOrderByIdDesc();

    @Query(value = "SELECT count(*) FROM MoviesModel WHERE active=true")
    Long movieCount();

    @Query(value = "SELECT count(*) FROM MoviesModel")
    Long movieCountAll();

    @Query("SELECT p FROM MoviesModel p WHERE " +
            "p.active=true AND (" +
            "p.title LIKE CONCAT('%',:query, '%')" +
            "Or p.description LIKE CONCAT('%', :query, '%'))")
    Iterable<MoviesModel> search(String query);
}
