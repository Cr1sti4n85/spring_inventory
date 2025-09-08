package inventory.system.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import inventory.system.enums.TransactionStatus;
import inventory.system.enums.TransactionType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TransactionRequest {

    @Positive(message = "El id del producto es obligatorio")
    private Long productId;

    @Positive(message = "La cantidad es obligatoria")
    private Integer quantity;

    private Long supplierId;

    private String description;

    private String note;

    @Enumerated(EnumType.STRING)
    private TransactionStatus status;

}