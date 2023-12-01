package tdtu.edu.vn.Backend.Models;

import com.fasterxml.jackson.annotation.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.annotation.Generated;
import jakarta.persistence.*;
import java.util.*;

@Entity
@Table(name = "series")
@Data
@NoArgsConstructor
public class SeriesModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    private String title;

    @Lob
    private String type;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "series_categories",
            joinColumns = { @JoinColumn(name = "series_id") },
            inverseJoinColumns = { @JoinColumn(name = "categories_id") }
    )
    List<CategoriesModel> categories = new ArrayList<>();

    @OneToMany(mappedBy = "series", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private List<MoviesModel> movies = new ArrayList<>();
    
    public MoviesModel getMovies() {
        return null;
    }

    public List<MoviesModel> moviesCustomGet() {
        return movies;
    }

    public int getMoviesCount() {
        return movies.size();
    }

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;
}
