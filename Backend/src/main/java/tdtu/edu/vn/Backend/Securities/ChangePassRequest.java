package tdtu.edu.vn.Backend.Securities;

import lombok.AllArgsConstructor;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
public class ChangePassRequest {
    @NotBlank
    private String oldPassword;

    @NotBlank
    private String newPassword;
}
