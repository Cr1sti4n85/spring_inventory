package inventory.system.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import inventory.system.enums.TransactionStatus;
import inventory.system.enums.TransactionType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class TransactionDTO {

    private Long id;

    @NotBlank(message = "El campo es obligatorio")
    private Integer totalProducts;

    @NotBlank(message = "El tipo de transacción es obligatorio")
    private TransactionType type;

    @NotBlank(message = "El estado de la transacción es obligatorio")
    private TransactionStatus status;

    private String description;

    private BigDecimal totalPrice;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private Long userId;

    private Long productId;

    private Long supplierId;



}