package tdtu.edu.vn.Backend.Utilities.Payloads.Home;

import tdtu.edu.vn.Backend.Models.BillingModel;
import tdtu.edu.vn.Backend.Models.MoviesModel;
import tdtu.edu.vn.Backend.Models.UsersModel;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AdminResponse {
    Iterable<UsersModel> users;
    Iterable<MoviesModel> movies;
    Long movieCountActive;
    Long movieCountAll;
    Iterable<BillingModel> billing;
    double billingSummary;
    public AdminResponse() {
    }

}
