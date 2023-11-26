package tdtu.edu.vn.Backend.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import tdtu.edu.vn.Backend.Models.MoviesModel;
import tdtu.edu.vn.Backend.Models.ReviewsModel;
import tdtu.edu.vn.Backend.Models.UsersModel;
import tdtu.edu.vn.Backend.Repositories.MoviesRepo;
import tdtu.edu.vn.Backend.Repositories.ReviewsRepo;
import tdtu.edu.vn.Backend.Repositories.UsersRepo;
import tdtu.edu.vn.Backend.Utilities.Payloads.GeneralResponse;
import tdtu.edu.vn.Backend.Utilities.Payloads.Admin.ReviewsAdminDTO;
import tdtu.edu.vn.Backend.Utilities.Payloads.Home.ReviewRequest;

import java.util.Optional;

@RestController
@RequestMapping("/api/review")
public class ReviewApi {
    @Autowired
    ReviewsRepo reviewsRepo;

    @Autowired
    UsersRepo usersRepo;

    @Autowired
    MoviesRepo moviesRepo;

    @GetMapping("{id}")
    public ResponseEntity<?> getById(@PathVariable Long id){
        Optional<ReviewsModel> data = reviewsRepo.findById(id);
        if(data.isPresent()){
            return ResponseEntity.ok(data.get());
        }
        return ResponseEntity.status(HttpServletResponse.SC_NOT_FOUND).body(new GeneralResponse(false, "Not found", null));
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody ReviewRequest request){
        try{
            ReviewsModel data = new ReviewsModel();
            UsersModel user = usersRepo.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
            Optional<MoviesModel> getMovie = moviesRepo.findById(request.getMovieId());
            if(getMovie.isPresent()){
                MoviesModel movie = getMovie.get();
                data.setRating(request.getRating());
                data.setUsers(user);
                data.setMovies(movie);
                data.setContent(request.getContent());
                data = reviewsRepo.save(data);
                return ResponseEntity.ok(data);
            }else{
                return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body(new GeneralResponse(false, "not found movie", null));
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body(new GeneralResponse(false, e.getMessage(), null));
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody ReviewsAdminDTO request){
        try{
            Optional<ReviewsModel> data = reviewsRepo.findById(id);
            UsersModel user = usersRepo.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
            if(data.isPresent()){
                ReviewsModel dataSave = data.get();
                if(dataSave.getUsers().getId() == user.getId()){
                    dataSave.setContent(request.getContent());
                    dataSave.setRating(request.getRating());
                    dataSave = reviewsRepo.save(dataSave);
                    return ResponseEntity.ok(dataSave);
                }else{
                    return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body(new GeneralResponse(false, "not permission", null));
                }
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
            UsersModel user = usersRepo.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
            if (data.isPresent()) {
                ReviewsModel review = data.get();
                if(review.getUsers().getId() == user.getId()){
                    reviewsRepo.delete(review);
                    return ResponseEntity.ok(new GeneralResponse(true, "success", null));
                }else{
                    return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body(new GeneralResponse(false, "not permission", null));
                }
            } else {
                return ResponseEntity.status(HttpServletResponse.SC_NOT_FOUND).body(new GeneralResponse(false, "not found", null));
            }
        }catch (Exception ex){
            return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body(new GeneralResponse(false, ex.getMessage(), null));
        }
    }
}
