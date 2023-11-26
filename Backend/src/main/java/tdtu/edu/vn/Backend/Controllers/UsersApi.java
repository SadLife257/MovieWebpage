package tdtu.edu.vn.Backend.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import tdtu.edu.vn.Backend.Models.UsersModel;
import tdtu.edu.vn.Backend.Repositories.UsersRepo;
import tdtu.edu.vn.Backend.Utilities.Payloads.GeneralResponse;
import tdtu.edu.vn.Backend.Utilities.Payloads.UsersRequest;

import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class UsersApi {

    @Autowired
    private UsersRepo usersRepo;

    @GetMapping()
    public ResponseEntity<?> get(){
        UsersModel data = usersRepo.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        if(data != null){
            return ResponseEntity.ok(data);
        }
        return ResponseEntity.status(HttpServletResponse.SC_NOT_FOUND).body(new GeneralResponse(false, "Not found", null));
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getById(@PathVariable Long id){
        Optional<UsersModel> data = usersRepo.findById(id);
        if(data.isPresent()){
            UsersModel user = data.get();
            if(user.isActive()){
                return ResponseEntity.ok(user);
            }else{
                return ResponseEntity.status(HttpServletResponse.SC_NOT_FOUND).body(new GeneralResponse(false, "User is banned", null));
            }
        }
        return ResponseEntity.status(HttpServletResponse.SC_NOT_FOUND).body(new GeneralResponse(false, "Not found", null));
    }

    @PutMapping()
    public ResponseEntity<?> update( @Valid @RequestBody UsersRequest request){
        try{
            UsersModel data = usersRepo.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
            if(data != null){
                if(request.getName() != null){
                    data.setName(request.getName());
                }
                if(request.getEmail()!= null){
                    data.setEmail(request.getEmail());
                }
                if(request.getAvatar() != null){
                    data.setAvatar(request.getAvatar());
                }
                usersRepo.save(data);
                return ResponseEntity.ok(data);
            }else{
                return ResponseEntity.status(HttpServletResponse.SC_NOT_FOUND).body(new GeneralResponse(false, "not found", null));
            }
        }catch (Exception ex){
            return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body(new GeneralResponse(false, ex.getMessage(), null));
        }
    }

}
