package tdtu.edu.vn.Backend.Models;

import com.fasterxml.uuid.Generators;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "reset_password")
@Data
@NoArgsConstructor
public class ResetPasswordModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long uid;

    @Column(unique = true)
    private String token = Generators.randomBasedGenerator().generate().toString();

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
}
