package tdtu.edu.vn.Backend.Utilities.Payloads;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UsersRequest {
    private String name;

    private String email;

    private String avatar;
}
