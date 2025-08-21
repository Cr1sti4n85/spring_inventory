package inventory.system.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {

    @Email(message = "El correo debe ser válido")
    private String email;

    @NotBlank(message = "El password es obligatorio")
    private String password;
}
