package inventory.system.dto;

import inventory.system.enums.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignupRequest {

    @NotBlank(message = "El nombre es obligatorio")
    private String name;

    @Email(message = "El correo debe ser válido")
    private String email;

    @NotBlank(message = "El password es obligatorio")
    private String password;

    private String phoneNumber;

    private UserRole role;
}
