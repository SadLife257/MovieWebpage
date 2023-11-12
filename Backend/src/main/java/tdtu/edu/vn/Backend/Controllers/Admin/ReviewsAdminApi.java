package tdtu.edu.vn.Backend.Controllers.Admin;

import tdtu.edu.vn.Backend.Models.ReviewsModel;
import tdtu.edu.vn.Backend.Repositories.ReviewsRepo;
import tdtu.edu.vn.Backend.Utilities.Payloads.Admin.ReviewsAdminDTO;
import tdtu.edu.vn.Backend.Utilities.Payloads.GeneralResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/api/admin/reviews")
public class ReviewsAdminApi {

    @Autowired
    private ReviewsRepo reviewsRepo;

    @GetMapping
    public ResponseEntity<?> get(){
        return ResponseEntity.ok(reviewsRepo.findAll());
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getById(@PathVariable Long id){
        Optional<ReviewsModel> data = reviewsRepo.findById(id);
        if(data.isPresent()){
            return ResponseEntity.ok(data.get());
        }
        return ResponseEntity.status(HttpServletResponse.SC_NOT_FOUND).body(new GeneralResponse(false, "Not found", null));
    }

    @PutMapping("{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody ReviewsAdminDTO request){
        try{
            Optional<ReviewsModel> data = reviewsRepo.findById(id);
            if(data.isPresent()){
                ReviewsModel dataSave = data.get();
                dataSave.setContent(request.getContent());
                dataSave.setRating(request.getRating());
                dataSave = reviewsRepo.save(dataSave);
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
            Optional<ReviewsModel> data = reviewsRepo.findById(id);
            if (data.isPresent()) {
                reviewsRepo.delete(data.get());
                return ResponseEntity.ok(new GeneralResponse(true, "success", null));
            } else {
                return ResponseEntity.status(HttpServletResponse.SC_NOT_FOUND).body(new GeneralResponse(false, "not found", null));
            }
        }catch (Exception ex){
            return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body(new GeneralResponse(false, ex.getMessage(), null));
        }
    }

}
