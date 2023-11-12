package tdtu.edu.vn.Backend.Utilities.Payloads.Admin;

import lombok.AllArgsConstructor;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import java.util.List;

@Data
@AllArgsConstructor
public class UsersAdminDTO {
    @NotBlank
    private String name;

    private String password;

    @NotBlank
    private String email;

    @NotBlank
    private String avatar;

    private List<String> roles;

    private String vip;

}
