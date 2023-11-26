package tdtu.edu.vn.Backend.Models;

import lombok.Data;
import lombok.NoArgsConstructor;
import tdtu.edu.vn.Backend.Utilities.JWT.JwtRefreshToken;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.*;
import java.util.*;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
public class UsersModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;

    private String password;

    private String email;

    private String name;

    private String avatar;

    private String roles = "ROLE_USER";

    private String refreshToken = (new JwtRefreshToken()).generate();

    private Date vip;

    private boolean active = true;

    @OneToMany(mappedBy="users", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private Set<ReviewsModel> reviews = new HashSet<>();

    // fix infinite loop
    public ReviewsModel getReviews() {
        return null;
    }

    public Set<ReviewsModel> reviewsCustomGet() {
        return reviews;
    }

//    public int getReviewsCount() {
//        return reviews.size();
//    }
    //end fix infinite loop

    @OneToMany(mappedBy="users", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private Set<ChatModel> chat = new HashSet<>();

    public ChatModel getChat() {
        return null;
    }

    public Set<ChatModel> chatCustomGet() {
        return chat;
    }

    @OneToMany(mappedBy="users", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private Set<BillingModel> billing = new HashSet<>();

    public BillingModel getBilling() {
        return null;
    }

    public Set<BillingModel> billingCustomGet() {
        return billing;
    }

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;
}
