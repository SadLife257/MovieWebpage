package tdtu.edu.vn.Backend.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import tdtu.edu.vn.Backend.Models.CategoriesModel;

public interface CategoriesRepo extends JpaRepository<CategoriesModel, Long> {
    CategoriesModel findByName(String name);

    @Query("SELECT p FROM CategoriesModel p WHERE " +
            "p.name LIKE CONCAT('%',:query, '%')")
    Iterable<CategoriesModel> search(String query);
}
