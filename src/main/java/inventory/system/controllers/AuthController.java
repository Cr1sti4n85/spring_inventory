package inventory.system.controllers;


import inventory.system.dto.LoginRequest;
import inventory.system.dto.Response;
import inventory.system.dto.SignupRequest;
import inventory.system.services.implementation.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<Response> registerUser(@RequestBody @Valid SignupRequest signupRequest) {
        return ResponseEntity.ok(userService.registerUser(signupRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<Response> loginUser(@RequestBody @Valid LoginRequest loginRequest) {
        return ResponseEntity.ok(userService.loginUser(loginRequest));
    }


}
