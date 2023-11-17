package tdtu.edu.vn.Backend.Controllers.Admin;

import tdtu.edu.vn.Backend.Models.CategoriesModel;
import tdtu.edu.vn.Backend.Models.SeriesModel;
import tdtu.edu.vn.Backend.Repositories.CategoriesRepo;
import tdtu.edu.vn.Backend.Repositories.MoviesRepo;
import tdtu.edu.vn.Backend.Repositories.SeriesRepo;
import tdtu.edu.vn.Backend.Utilities.Payloads.Admin.CategoriesAdminDTO;
import tdtu.edu.vn.Backend.Utilities.Payloads.Admin.SeriesAdminDTO;
import tdtu.edu.vn.Backend.Utilities.Responses.Admin.CategoriesAdminResponse;
import tdtu.edu.vn.Backend.Utilities.Responses.Admin.SeriesAdminResponse;
import tdtu.edu.vn.Backend.Utilities.Constants;
import tdtu.edu.vn.Backend.Utilities.Payloads.GeneralResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    public ResponseEntity<?> get(
    		@RequestParam(value = "pageNo", defaultValue = Constants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = Constants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = Constants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = Constants.DEFAULT_SORT_DIRECTION, required = false) String sortDir
    		){
    	Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<SeriesModel> series = seriesRepo.findAll(pageable);
        
        List<SeriesModel> SeriesList = series.getContent();
        
        SeriesAdminResponse seriesAdminResponse = new SeriesAdminResponse();
        seriesAdminResponse.setContent(SeriesList);
        seriesAdminResponse.setPageNo(series.getNumber());
        seriesAdminResponse.setPageSize(series.getSize());
        seriesAdminResponse.setTotalElements(series.getTotalElements());
        seriesAdminResponse.setTotalPages(series.getTotalPages());
        seriesAdminResponse.setLast(series.isLast());
        return ResponseEntity.ok(seriesAdminResponse);
    }

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
    public ResponseEntity<?> create(@Valid @RequestBody SeriesAdminDTO request){
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
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody SeriesAdminDTO request){
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
