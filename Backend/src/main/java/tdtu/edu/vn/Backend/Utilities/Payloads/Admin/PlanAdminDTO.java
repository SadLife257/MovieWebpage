package tdtu.edu.vn.Backend.Utilities.Payloads.Admin;

import lombok.AllArgsConstructor;
import lombok.Data;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
public class PlanAdminDTO {
    @NotBlank
    String name;

    @DecimalMin("0.0")
    double price;

    String description;

    @DecimalMin("0.0")
    Long days;

}
