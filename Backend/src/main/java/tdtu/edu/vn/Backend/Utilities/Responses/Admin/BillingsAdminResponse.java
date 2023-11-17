package tdtu.edu.vn.Backend.Utilities.Responses.Admin;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tdtu.edu.vn.Backend.Models.BillingModel;
import tdtu.edu.vn.Backend.Models.CategoriesModel;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BillingsAdminResponse {
	private List<BillingModel> content;
    private int pageNo;
    private int pageSize;
    private long totalElements;
    private int totalPages;
    private boolean last;
}
