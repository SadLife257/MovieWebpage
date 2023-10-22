package tdtu.edu.vn.Backend.Utilities.Payloads;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GeneralResponse {
    private boolean success;
    private String message;
    private Object data;
}
