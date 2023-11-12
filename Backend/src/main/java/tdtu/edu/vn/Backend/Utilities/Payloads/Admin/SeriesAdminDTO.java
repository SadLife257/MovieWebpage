package tdtu.edu.vn.Backend.Utilities.Payloads.Admin;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotBlank;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SeriesAdminDTO {
    @NotBlank
    String title;

    @NotBlank
    String type;

    List<Long> categories;
}
