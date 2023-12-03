package tdtu.edu.vn.Backend.Controllers.Admin;

import tdtu.edu.vn.Backend.Models.BillingModel;
import tdtu.edu.vn.Backend.Models.CategoriesModel;
import tdtu.edu.vn.Backend.Models.UsersModel;
import tdtu.edu.vn.Backend.Repositories.BillingRepo;
import tdtu.edu.vn.Backend.Utilities.Constants;
import tdtu.edu.vn.Backend.Utilities.Payloads.GeneralResponse;
import tdtu.edu.vn.Backend.Utilities.Responses.Admin.BillingsAdminResponse;
import tdtu.edu.vn.Backend.Utilities.Responses.Admin.CategoriesAdminResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/admin/billing")
public class BillingAdminApi {
    @Autowired
    private BillingRepo billingRepo;
    
//    @GetMapping
//    public ResponseEntity<?> get(
//    		@RequestParam(value = "pageNo", defaultValue = Constants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
//            @RequestParam(value = "pageSize", defaultValue = Constants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
//            @RequestParam(value = "sortBy", defaultValue = Constants.DEFAULT_SORT_BY, required = false) String sortBy,
//            @RequestParam(value = "sortDir", defaultValue = Constants.DEFAULT_SORT_DIRECTION, required = false) String sortDir
//    		){
//    	Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
//                : Sort.by(sortBy).descending();
//
//        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
//
//        Page<BillingModel> billings = billingRepo.findAll(pageable);
//        
//        List<BillingModel> BillingList = billings.getContent();
//        
//        BillingsAdminResponse billingAdminResponse = new BillingsAdminResponse();
//        billingAdminResponse.setContent(BillingList);
//        billingAdminResponse.setPageNo(billings.getNumber());
//        billingAdminResponse.setPageSize(billings.getSize());
//        billingAdminResponse.setTotalElements(billings.getTotalElements());
//        billingAdminResponse.setTotalPages(billings.getTotalPages());
//        billingAdminResponse.setLast(billings.isLast());
//        return ResponseEntity.ok(billingAdminResponse);
//    }

    @GetMapping
    public ResponseEntity<?> get(){
        return ResponseEntity.ok(billingRepo.findAll());
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getById(@PathVariable Long id){
        Optional<BillingModel> data = billingRepo.findById(id);
        if(data.isPresent()){
            return ResponseEntity.ok(data.get());
        }
        return ResponseEntity.status(HttpServletResponse.SC_NOT_FOUND).body(new GeneralResponse(false, "Not found", null));
    }

    @PutMapping("{id}/confirm")
    public ResponseEntity<?> confirm(@PathVariable Long id){
        try{
            Optional<BillingModel> data = billingRepo.findById(id);
            if(data.isPresent()){
                BillingModel dataSave = data.get();
                if(dataSave.isConfirmed()){
                    return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body(new GeneralResponse(false, "Confirmed", null));
                }
                dataSave.setConfirmed(true);
                UsersModel user = dataSave.getUsers();

                Date today = new Date();
                if(user.getVip() != null && user.getVip().after(today)){
                    user.setVip(new Date(user.getVip().getTime() + (1000 * 60 * 60 * 24 * dataSave.getPlan().getDays())));
                }else{
                    user.setVip(new Date(today.getTime() + (1000 * 60 * 60 * 24 * dataSave.getPlan().getDays())));
                }
                dataSave.setUsers(user);
                dataSave = billingRepo.save(dataSave);
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
            Optional<BillingModel> data = billingRepo.findById(id);
            if (data.isPresent()) {
                billingRepo.delete(data.get());
                return ResponseEntity.ok(new GeneralResponse(true, "success", null));
            } else {
                return ResponseEntity.status(HttpServletResponse.SC_NOT_FOUND).body(new GeneralResponse(false, "not found", null));
            }
        }catch (Exception ex){
            return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body(new GeneralResponse(false, ex.getMessage(), null));
        }
    }
}
