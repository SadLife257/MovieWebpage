package tdtu.edu.vn.Backend.Models;

import com.fasterxml.jackson.annotation.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "reviews")
@Data
@NoArgsConstructor
public class ReviewsModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="movies_id", nullable=false)
    private MoviesModel movies;

    // fix infinite loop
    public MoviesModel getMovies() {
        return null;
    }

    public Long getMoviesId(){
        return movies.getId();
    }

    public MoviesModel moviesCustomGet() {
        return movies;
    }
    //end fix infinite loop

    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    private UsersModel users;

    private float rating;

    @Lob
    private String content;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;


}
