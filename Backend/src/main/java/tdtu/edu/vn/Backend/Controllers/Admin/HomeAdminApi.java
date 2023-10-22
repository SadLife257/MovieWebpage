package tdtu.edu.vn.Backend.Controllers.Admin;

import tdtu.edu.vn.Backend.Models.BillingModel;
import tdtu.edu.vn.Backend.Repositories.BillingRepo;
import tdtu.edu.vn.Backend.Repositories.MoviesRepo;
import tdtu.edu.vn.Backend.Repositories.UsersRepo;
import tdtu.edu.vn.Backend.Utilities.Payloads.Home.AdminAnalysisResponse;
import tdtu.edu.vn.Backend.Utilities.Payloads.Home.AdminResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Year;
import java.util.Calendar;
import java.util.List;

@RestController
@RequestMapping("/api/admin/home")
public class HomeAdminApi {

    @Autowired
    UsersRepo  usersRepo;

    @Autowired
    MoviesRepo  moviesRepo;

    @Autowired
    BillingRepo  billingRepo;

    @GetMapping
    public ResponseEntity<?> get(){
        AdminResponse result = new AdminResponse();
        result.setUsers(usersRepo.findTop5ByOrderByIdDesc());
        result.setMovies(moviesRepo.findTop5ByOrderByIdDesc());
        result.setMovieCountActive(moviesRepo.movieCount());
        result.setMovieCountAll(moviesRepo.movieCountAll());
        result.setBilling(billingRepo.findTop5ByOrderByIdDesc());
        result.setBillingSummary(billingRepo.billingSummary());
        return ResponseEntity.ok(result);
    }

    @GetMapping("users")
    public ResponseEntity<?> getUsers(){
        return ResponseEntity.ok(usersRepo.findTop5ByOrderByIdDesc());
    }

    @GetMapping("movies")
    public ResponseEntity<?> getMovies(){
        return ResponseEntity.ok(moviesRepo.findTop5ByOrderByIdDesc());
    }

    @GetMapping("movie-count")
    public ResponseEntity<?> getMovieCount(){
        return ResponseEntity.ok(moviesRepo.movieCount());
    }

    @GetMapping("movie-count-all")
    public ResponseEntity<?> getMovieCountAll(){
        return ResponseEntity.ok(moviesRepo.movieCountAll());
    }

    @GetMapping("billing")
    public ResponseEntity<?> getBilling(){
        return ResponseEntity.ok(billingRepo.findTop5ByOrderByIdDesc());
    }

    @GetMapping("billing-summary")
    public ResponseEntity<?> getBillingSummary(){
        if(billingRepo.billingConfirmCount() > 0){
            return ResponseEntity.ok(billingRepo.billingSummary());
        }else{
            return ResponseEntity.ok(0);
        }
    }

    @GetMapping("analysis")
    public ResponseEntity<?> getAnalysisCurrentYear(){
        AdminAnalysisResponse result = new AdminAnalysisResponse();
        for(BillingModel bill : billingRepo.findByCreateYear(Year.now().getValue())){
            setResultAnalysis(result, bill);
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping("analysis/{year}")
    public ResponseEntity<?> getAnalysisCustomYear(@PathVariable int year){
        AdminAnalysisResponse result = new AdminAnalysisResponse();
        for(BillingModel bill : billingRepo.findByCreateYear(year)){
            setResultAnalysis(result, bill);
        }
        return ResponseEntity.ok(result);
    }

    private void setResultAnalysis(AdminAnalysisResponse result, BillingModel bill) {
        switch (bill.getCreatedAt().getMonth()){
            case Calendar.JANUARY:
                result.setJanuary(result.getJanuary() + bill.getAmount());
                break;
            case Calendar.FEBRUARY:
                result.setFebruary(result.getFebruary() + bill.getAmount());
                break;
            case Calendar.MARCH:
                result.setMarch(result.getMarch() + bill.getAmount());
                break;
            case Calendar.APRIL:
                result.setApril(result.getApril() + bill.getAmount());
                break;
            case Calendar.MAY:
                result.setMay(result.getMay() + bill.getAmount());
                break;
            case Calendar.JUNE:
                result.setJune(result.getJune() + bill.getAmount());
                break;
            case Calendar.JULY:
                result.setJuly(result.getJuly() + bill.getAmount());
                break;
            case Calendar.AUGUST:
                result.setAugust(result.getAugust() + bill.getAmount());
                break;
            case Calendar.SEPTEMBER:
                result.setSeptember(result.getSeptember() + bill.getAmount());
                break;
            case Calendar.OCTOBER:
                result.setOctober(result.getOctober() + bill.getAmount());
                break;
            case Calendar.NOVEMBER:
                result.setNovember(result.getNovember() + bill.getAmount());
                break;
            case Calendar.DECEMBER:
                result.setDecember(result.getDecember() + bill.getAmount());
                break;
        }
    }

}
