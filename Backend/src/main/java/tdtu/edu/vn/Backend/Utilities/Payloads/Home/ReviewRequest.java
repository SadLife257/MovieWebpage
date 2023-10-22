package tdtu.edu.vn.Backend.Utilities.Payloads.Home;

import lombok.AllArgsConstructor;
import lombok.Data;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
public class ReviewRequest {
    @NotBlank
    private String content;

    @DecimalMin("0.0")
    @DecimalMax("5.0")
    private float rating;

    @DecimalMin("0.0")
    private Long movieId;

}
