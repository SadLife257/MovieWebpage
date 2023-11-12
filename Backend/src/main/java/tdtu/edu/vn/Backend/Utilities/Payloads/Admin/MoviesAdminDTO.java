package tdtu.edu.vn.Backend.Utilities.Payloads.Admin;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MoviesAdminDTO {
    @NotBlank
    private String title;

    private String description;

    @NotBlank
    private String imgTitle;

    @NotBlank
    private String imgSm;

    @NotBlank
    private String trailer;

    @NotBlank
    private String video;

    @NotBlank
    private String year;

    @NotNull
    private int limitAge;

    private boolean active = true;

    private boolean vip = false;

    List<Long> categories = null;

    private Long series = null;
}
