package tdtu.edu.vn.Backend.Utilities.Payloads.Home;

import lombok.AllArgsConstructor;
import lombok.Data;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
public class BillingRequest {
    @DecimalMin("0.0")
    double amount;

    @NotBlank
    String payment;

    String description;

    @DecimalMin("0.0")
    Long planId;
}
