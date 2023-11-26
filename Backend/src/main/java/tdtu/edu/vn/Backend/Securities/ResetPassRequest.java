package tdtu.edu.vn.Backend.Securities;

import lombok.AllArgsConstructor;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
public class ResetPassRequest {
    @NotBlank
    String username;

    @NotBlank
    String email;

    @NotBlank
    String path;
}
