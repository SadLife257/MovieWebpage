package tdtu.edu.vn.Backend.Controllers.Admin;

import tdtu.edu.vn.Backend.Models.CategoriesModel;
import tdtu.edu.vn.Backend.Models.SeriesModel;
import tdtu.edu.vn.Backend.Repositories.CategoriesRepo;
import tdtu.edu.vn.Backend.Repositories.MoviesRepo;
import tdtu.edu.vn.Backend.Repositories.SeriesRepo;
import tdtu.edu.vn.Backend.Utilities.Payloads.Admin.CategoriesAdminDTO;
import tdtu.edu.vn.Backend.Utilities.Responses.Admin.CategoriesAdminResponse;
import tdtu.edu.vn.Backend.Utilities.Payloads.GeneralResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import tdtu.edu.vn.Backend.Utilities.Constants;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/admin/categories")
public class CategoriesAdminApi {

    @Autowired
    CategoriesRepo  categoriesRepo;

    @Autowired
    SeriesRepo  seriesRepo;

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

        Page<CategoriesModel> categories = categoriesRepo.findAll(pageable);
        
        List<CategoriesModel> CategoryList = categories.getContent();
        
        CategoriesAdminResponse categoriesAdminResponse = new CategoriesAdminResponse();
        categoriesAdminResponse.setContent(CategoryList);
        categoriesAdminResponse.setPageNo(categories.getNumber());
        categoriesAdminResponse.setPageSize(categories.getSize());
        categoriesAdminResponse.setTotalElements(categories.getTotalElements());
        categoriesAdminResponse.setTotalPages(categories.getTotalPages());
        categoriesAdminResponse.setLast(categories.isLast());
        return ResponseEntity.ok(categoriesAdminResponse);
    }

    @GetMapping
    public ResponseEntity<?> get(){
        return ResponseEntity.ok(categoriesRepo.findAll());
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getById(@PathVariable Long id){
        Optional<CategoriesModel> data = categoriesRepo.findById(id);
        if(data.isPresent()){
            return ResponseEntity.ok(data.get());
        }
        return ResponseEntity.status(HttpServletResponse.SC_NOT_FOUND).body(new GeneralResponse(false, "Not found", null));
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody CategoriesAdminDTO request){
        try{
            CategoriesModel data = new CategoriesModel();
            data.setName(request.getName());
            CategoriesModel dataSave = categoriesRepo.save(data);
            return ResponseEntity.ok(dataSave);
        }catch (Exception e){
            return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body(new GeneralResponse(false, e.getMessage(), null));
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody CategoriesAdminDTO request){
        try{
            Optional<CategoriesModel> data = categoriesRepo.findById(id);
            if(data.isPresent()){
                CategoriesModel dataSave = data.get();
                dataSave.setName(request.getName());
                dataSave = categoriesRepo.save(dataSave);
                return ResponseEntity.ok(new GeneralResponse(true, "success", dataSave));
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
            Optional<CategoriesModel> data = categoriesRepo.findById(id);
            if (data.isPresent()) {
                CategoriesModel categories = data.get();
                moviesRepo.deleteAll(categories.moviesCustomGet());
                seriesRepo.deleteAll(categories.seriesCustomGet());
                categoriesRepo.delete(categories);
                return ResponseEntity.ok(new GeneralResponse(true, "success", null));
            } else {
                return ResponseEntity.status(HttpServletResponse.SC_NOT_FOUND).body(new GeneralResponse(false, "not found", null));
            }
        }catch (Exception ex){
            return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body(new GeneralResponse(false, ex.getMessage(), null));
        }
    }
}
