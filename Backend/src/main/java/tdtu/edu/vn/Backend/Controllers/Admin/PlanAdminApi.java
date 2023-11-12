package tdtu.edu.vn.Backend.Controllers.Admin;

import tdtu.edu.vn.Backend.Models.PlanModel;
import tdtu.edu.vn.Backend.Repositories.BillingRepo;
import tdtu.edu.vn.Backend.Repositories.PlanRepo;
import tdtu.edu.vn.Backend.Utilities.Payloads.Admin.PlanAdminDTO;
import tdtu.edu.vn.Backend.Utilities.Payloads.GeneralResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/api/admin/plan")
public class PlanAdminApi {
    @Autowired
    private PlanRepo planRepo;

    @Autowired
    BillingRepo  billingRepo;

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

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody PlanAdminDTO request){
        try{
            PlanModel data = new PlanModel();
            data.setName(request.getName());
            data.setDescription(request.getDescription());
            data.setPrice(request.getPrice());
            data.setDays(request.getDays());
            PlanModel dataSave = planRepo.save(data);
            return ResponseEntity.ok(dataSave);
        }catch (Exception e){
            return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body(new GeneralResponse(false, e.getMessage(), null));
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody PlanAdminDTO request){
        try{
            Optional<PlanModel> data = planRepo.findById(id);
            if(data.isPresent()){
                PlanModel dataSave = data.get();
                dataSave.setName(request.getName());
                dataSave.setDescription(request.getDescription());
                dataSave.setPrice(request.getPrice());
                dataSave.setDays(request.getDays());
                dataSave = planRepo.save(dataSave);
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
            Optional<PlanModel> data = planRepo.findById(id);
            if (data.isPresent()) {
                PlanModel plan = data.get();
                billingRepo.deleteAll(plan.billingCustomGet());
                planRepo.delete(plan);
                return ResponseEntity.ok(new GeneralResponse(true, "success", null));
            } else {
                return ResponseEntity.status(HttpServletResponse.SC_NOT_FOUND).body(new GeneralResponse(false, "not found", null));
            }
        }catch (Exception ex){
            return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body(new GeneralResponse(false, ex.getMessage(), null));
        }
    }
}
