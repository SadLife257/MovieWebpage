package tdtu.edu.vn.Backend.Models;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.*;
import java.util.*;

@Entity
@Table(name = "movies")
@Data
@NoArgsConstructor
public class MoviesModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    private String title;

    @Lob
    private String description;

    private String imgTitle;

    private String imgSm;

    private String trailer;

    private String video;

    private String year;

    private boolean active = true;

    private boolean vip = false;

    private int limitAge;

    private Long views = Long.parseLong("0");

    @ManyToOne
    @JoinColumn(name="series_id")
    private SeriesModel series;

//    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @ManyToMany
    @JoinTable(
            name = "movies_categories",
            joinColumns = { @JoinColumn(name = "movies_id") },
            inverseJoinColumns = { @JoinColumn(name = "categories_id") }
    )
    List<CategoriesModel> categories = new ArrayList<>();

    @OneToMany(mappedBy="movies", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private List<ReviewsModel> reviews = new ArrayList<>();

    public double getAverageRating(){
        if(reviews.size() > 0){
            double rating = 0.0;
            for (ReviewsModel review : reviews){
                rating += review.getRating();
            }
            return rating / reviews.size();
        }else{
            return 0.0;
        }

    }

    public int getReviewsCount(){
        return reviews.size();
    }

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;
}
