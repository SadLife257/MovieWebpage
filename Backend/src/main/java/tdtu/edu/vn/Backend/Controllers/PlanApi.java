package tdtu.edu.vn.Backend.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletResponse;
import tdtu.edu.vn.Backend.Models.PlanModel;
import tdtu.edu.vn.Backend.Repositories.PlanRepo;
import tdtu.edu.vn.Backend.Utilities.Payloads.GeneralResponse;

import java.util.Optional;

@RestController
@RequestMapping("/api/plan")
public class PlanApi {
    @Autowired
    private PlanRepo planRepo;

    @GetMapping
    public ResponseEntity<?> get(){
        return ResponseEntity.ok(planRepo.findAll());
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getById(@PathVariable Long id){
        Optional<PlanModel> data = planRepo.findById(id);
        if(data.isPresent()){
            return ResponseEntity.ok(data.get());
        }
        return ResponseEntity.status(HttpServletResponse.SC_NOT_FOUND).body(new GeneralResponse(false, "Not found", null));
    }
}
