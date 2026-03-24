package phattrienungdungvoij2ee.bai5_qlsp_jpa.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "payment")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "subscription_id", nullable = false)
    private Subscription subscription;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal amount;

    // DA_THANH_TOAN or CHUA_THANH_TOAN
    @Column(nullable = false, length = 20)
    private String status;

    @Column(name = "due_date")
    private LocalDate dueDate;
}
