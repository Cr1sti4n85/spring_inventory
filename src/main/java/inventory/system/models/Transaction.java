package inventory.system.models;

import inventory.system.enums.TransactionStatus;
import inventory.system.enums.TransactionType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "transactions")
@Getter
@Setter
@Builder
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotBlank(message = "El campo es obligatorio")
    private Integer totalProducts;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @NotBlank(message = "El tipo de transacción es obligatorio")
    private TransactionType type;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @NotBlank(message = "El estado de la transacción es obligatorio")
    private TransactionStatus status;

    private String description;

    @Column(nullable = false)
    private BigDecimal totalPrice;


    private final LocalDateTime createAt = LocalDateTime.now();

    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", totalProducts=" + totalProducts +
                ", type=" + type +
                ", status=" + status +
                ", totalPrice=" + totalPrice +
                ", createAt=" + createAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}