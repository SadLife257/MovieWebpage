package tdtu.edu.vn.Backend.Controllers.Admin;

import tdtu.edu.vn.Backend.Models.CategoriesModel;
import tdtu.edu.vn.Backend.Models.SeriesModel;
import tdtu.edu.vn.Backend.Repositories.CategoriesRepo;
import tdtu.edu.vn.Backend.Repositories.MoviesRepo;
import tdtu.edu.vn.Backend.Repositories.SeriesRepo;
import tdtu.edu.vn.Backend.Utilities.Payloads.Admin.CategoriesAdminRequest;
import tdtu.edu.vn.Backend.Utilities.Payloads.Admin.SeriesAdminRequest;
import tdtu.edu.vn.Backend.Utilities.Payloads.GeneralResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api/admin/series")
public class SeriesAdminApi {

    @Autowired
    private SeriesRepo seriesRepo;

    @Autowired
    private CategoriesRepo categoriesRepo;

    @Autowired
    MoviesRepo  moviesRepo;

    @GetMapping
    public ResponseEntity<?> get(){
        return ResponseEntity.ok(seriesRepo.findAll());
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getById(@PathVariable Long id){
        Optional<SeriesModel> data = seriesRepo.findById(id);
        if(data.isPresent()){
            return ResponseEntity.ok(data.get());
        }
        return ResponseEntity.status(HttpServletResponse.SC_NOT_FOUND).body(new GeneralResponse(false, "Not found", null));
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody SeriesAdminRequest request){
        try{
            SeriesModel data = new SeriesModel();
            data.setTitle(request.getTitle());
            data.setType(request.getType());
            if(request.getCategories() != null)
            {
            	List<CategoriesModel> categories = (List<CategoriesModel>) categoriesRepo.findAllById(request.getCategories());
                data.getCategories().addAll(categories);
            }
            SeriesModel dataSave = seriesRepo.save(data);
            return ResponseEntity.ok(dataSave);
        }catch (Exception e){
            return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body(new GeneralResponse(false, e.getMessage(), null));
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody SeriesAdminRequest request){
        try{
            Optional<SeriesModel> data = seriesRepo.findById(id);
            if(data.isPresent()){
                SeriesModel dataSave = data.get();
                dataSave.setTitle(request.getTitle());
                dataSave.setType(request.getType());
                if(request.getCategories().size() > 0){
                    List<CategoriesModel> categories = (List<CategoriesModel>) categoriesRepo.findAllById(request.getCategories());
                    dataSave.getCategories().clear();
                    dataSave.getCategories().addAll(categories);
                }else if(dataSave.getCategories() != null){
                    dataSave.getCategories().clear();
                }
                dataSave = seriesRepo.save(dataSave);
                return ResponseEntity.ok(dataSave);
            }else{
                return ResponseEntity.status(HttpServletResponse.SC_NOT_FOUND).body(new GeneralResponse(false, "not found", null));
            }
        }catch (Exception ex){
            return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body(new GeneralResponse(false, ex.getMessage(), null));
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        try {
            Optional<SeriesModel> data = seriesRepo.findById(id);
            if (data.isPresent()) {
                SeriesModel series = data.get();
                moviesRepo.deleteAll(series.moviesCustomGet());
                seriesRepo.delete(series);
                return ResponseEntity.ok(new GeneralResponse(true, "success", null));
            } else {
                return ResponseEntity.status(HttpServletResponse.SC_NOT_FOUND).body(new GeneralResponse(false, "not found", null));
            }
        }catch (Exception ex){
            return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body(new GeneralResponse(false, ex.getMessage(), null));
        }
    }
}
