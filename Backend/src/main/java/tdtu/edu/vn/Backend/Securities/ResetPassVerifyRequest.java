package tdtu.edu.vn.Backend.Securities;

import lombok.AllArgsConstructor;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
public class ResetPassVerifyRequest {
    @NotBlank
    String token;

    @NotBlank
    String password;
}
