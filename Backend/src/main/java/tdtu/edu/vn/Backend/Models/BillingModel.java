package tdtu.edu.vn.Backend.Models;

import com.fasterxml.uuid.Generators;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Entity
@Table(name = "billing")
@Data
@NoArgsConstructor
public class BillingModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double amount;

    private boolean confirmed = false;

    private String payment;

    @Column(unique = true)
    private String code = Generators.randomBasedGenerator().generate().toString();

    @Lob
    private String description;

    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    private UsersModel users;

    @ManyToOne
    @JoinColumn(name="plan_id", nullable=false)
    private PlanModel plan;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;
}
