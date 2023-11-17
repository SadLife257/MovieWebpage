package tdtu.edu.vn.Backend.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import tdtu.edu.vn.Backend.Models.SeriesModel;

public interface SeriesRepo extends JpaRepository<SeriesModel, Long> {
    @Query("SELECT p FROM SeriesModel p WHERE " +
            "p.title LIKE CONCAT('%',:query, '%')")
    Iterable<SeriesModel> search(String query);
}
