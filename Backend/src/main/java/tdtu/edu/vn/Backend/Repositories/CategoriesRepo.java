package tdtu.edu.vn.Backend.Repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import tdtu.edu.vn.Backend.Models.CategoriesModel;

@Repository
public interface CategoriesRepo extends CrudRepository<CategoriesModel, Long> {
    CategoriesModel findByName(String name);

    @Query("SELECT p FROM CategoriesModel p WHERE " +
            "p.name LIKE CONCAT('%',:query, '%')")
    Iterable<CategoriesModel> search(String query);
}
