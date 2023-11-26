package tdtu.edu.vn.Backend.Securities;

import lombok.AllArgsConstructor;
import lombok.Data;
import tdtu.edu.vn.Backend.Models.UsersModel;

@Data
@AllArgsConstructor
public class SignupResponse {
    private boolean success;

    private String message;

    private String token;

    private UsersModel user;
}
