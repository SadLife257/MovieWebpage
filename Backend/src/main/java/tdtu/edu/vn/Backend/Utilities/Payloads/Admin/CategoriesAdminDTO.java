package tdtu.edu.vn.Backend.Utilities.Payloads.Admin;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoriesAdminDTO {
    @NotBlank
    private String name;
}
