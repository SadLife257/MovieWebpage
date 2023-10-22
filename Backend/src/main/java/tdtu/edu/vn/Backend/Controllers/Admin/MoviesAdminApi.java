package tdtu.edu.vn.Backend.Controllers.Admin;

import tdtu.edu.vn.Backend.Models.CategoriesModel;
import tdtu.edu.vn.Backend.Models.MoviesModel;
import tdtu.edu.vn.Backend.Models.SeriesModel;
import tdtu.edu.vn.Backend.Repositories.CategoriesRepo;
import tdtu.edu.vn.Backend.Repositories.MoviesRepo;
import tdtu.edu.vn.Backend.Repositories.ReviewsRepo;
import tdtu.edu.vn.Backend.Repositories.SeriesRepo;
import tdtu.edu.vn.Backend.Utilities.Payloads.Admin.MoviesAdminRequest;
import tdtu.edu.vn.Backend.Utilities.Payloads.GeneralResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/admin/movies")
public class MoviesAdminApi {
    @Autowired
    private MoviesRepo moviesRepo;

    @Autowired
    private CategoriesRepo categoriesRepo;

    @Autowired
    private SeriesRepo seriesRepo;

    @Autowired
    ReviewsRepo  reviewsRepo;

    @GetMapping
    public ResponseEntity<?> get(){
        return ResponseEntity.ok(moviesRepo.findAll());
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getById(@PathVariable Long id){
        Optional<MoviesModel> data = moviesRepo.findById(id);
        if(data.isPresent()){
            return ResponseEntity.ok(data.get());
        }
        return ResponseEntity.status(HttpServletResponse.SC_NOT_FOUND).body(new GeneralResponse(false, "Not found", null));
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody MoviesAdminRequest request){
        try{
            MoviesModel data = new MoviesModel();
            data.setTitle(request.getTitle());
            data.setDescription(request.getDescription());
            data.setImgTitle(request.getImgTitle());
            data.setImgSm(request.getImgSm());
            data.setTrailer(request.getTrailer());
            data.setVideo(request.getVideo());
            data.setYear(request.getYear());
            data.setLimitAge(request.getLimitAge());
            data.setActive(request.isActive());
            data.setVip(request.isVip());
            if(request.getSeries() != null){
                Optional<SeriesModel> seriesTmp = seriesRepo.findById(request.getSeries());
                if(seriesTmp.isPresent()){
                    data.setSeries(seriesTmp.get());
                }else {
                    return ResponseEntity.status(HttpServletResponse.SC_NOT_FOUND).body(new GeneralResponse(false, "not found series", null));
                }
            }
            if(request.getCategories() != null)
            {
            	List<CategoriesModel> categories = (List<CategoriesModel>) categoriesRepo.findAllById(request.getCategories());
                data.getCategories().addAll(categories);
            }
            MoviesModel dataSave = moviesRepo.save(data);
            return ResponseEntity.ok(dataSave);
        }catch (Exception e){
            e.printStackTrace();
        	return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body(new GeneralResponse(false, e.getMessage(), null));
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody MoviesAdminRequest request){
        try{
            Optional<MoviesModel> data = moviesRepo.findById(id);
            if(data.isPresent()){
                MoviesModel dataSave = data.get();
                dataSave.setDescription(request.getDescription());
                dataSave.setTitle(request.getTitle());
                dataSave.setImgSm(request.getImgSm());
                dataSave.setImgTitle(request.getImgTitle());
                dataSave.setTrailer(request.getTrailer());
                dataSave.setVideo(request.getVideo());
                dataSave.setYear(request.getYear());
                dataSave.setLimitAge(request.getLimitAge());
                dataSave.setActive(request.isActive());
                dataSave.setVip(request.isVip());
                if(request.getSeries() != null){
                    Optional<SeriesModel> seriesTmp = seriesRepo.findById(request.getSeries());
                    if(seriesTmp.isPresent()){
                        dataSave.setSeries(seriesTmp.get());
                    }else {
                        return ResponseEntity.status(HttpServletResponse.SC_NOT_FOUND).body(new GeneralResponse(false, "not found series", null));
                    }
                }
                if(request.getCategories().size() > 0){
                    List<CategoriesModel> categories = (List<CategoriesModel>) categoriesRepo.findAllById(request.getCategories());
                    dataSave.getCategories().clear();
                    dataSave.getCategories().addAll(categories);
                }else if(dataSave.getCategories() != null){
                    dataSave.getCategories().clear();
                }
                dataSave = moviesRepo.save(dataSave);
                return ResponseEntity.ok(dataSave);
            }else{
                return ResponseEntity.status(HttpServletResponse.SC_NOT_FOUND).body(new GeneralResponse(false, "not found", null));
            }
        }catch (Exception ex){
            return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body(new GeneralResponse(false, ex.getMessage(), null));
        }
    }

    @PutMapping("{id}/show")
    public ResponseEntity<?> updateShowTrue(@PathVariable Long id){
        try{
            Optional<MoviesModel> data = moviesRepo.findById(id);
            if(data.isPresent()){
                MoviesModel dataSave = data.get();
                dataSave.setActive(true);
                dataSave = moviesRepo.save(dataSave);
                return ResponseEntity.ok(dataSave);
            }else{
                return ResponseEntity.status(HttpServletResponse.SC_NOT_FOUND).body(new GeneralResponse(false, "not found", null));
            }
        }catch (Exception ex){
            return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body(new GeneralResponse(false, ex.getMessage(), null));
        }
    }

    @PutMapping("{id}/hide")
    public ResponseEntity<?> updateShowFalse(@PathVariable Long id){
        try{
            Optional<MoviesModel> data = moviesRepo.findById(id);
            if(data.isPresent()){
                MoviesModel dataSave = data.get();
                dataSave.setActive(false);
                dataSave = moviesRepo.save(dataSave);
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
            Optional<MoviesModel> data = moviesRepo.findById(id);
            if (data.isPresent()) {
                MoviesModel movie = data.get();
                reviewsRepo.deleteAll(movie.getReviews());
                moviesRepo.delete(movie);
                return ResponseEntity.ok(new GeneralResponse(true, "success", null));
            } else {
                return ResponseEntity.status(HttpServletResponse.SC_NOT_FOUND).body(new GeneralResponse(false, "not found", null));
            }
        }catch (Exception ex){
            return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body(new GeneralResponse(false, ex.getMessage(), null));
        }
    }


}
