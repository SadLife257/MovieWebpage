package tdtu.edu.vn.Backend.Utilities.Payloads.Home;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AdminAnalysisResponse {
    private double january  = 0;
    private double february = 0;
    private double march  = 0;
    private double april = 0;
    private double may = 0;
    private double june = 0;
    private double july = 0;
    private double august = 0;
    private double september = 0;
    private double october = 0;
    private double november = 0;
    private double december = 0;

    public AdminAnalysisResponse() {

    }
}
