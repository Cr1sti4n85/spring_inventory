package inventory.system.controllers;

import inventory.system.dto.JwtResponse;
import inventory.system.dto.LoginRequest;
import inventory.system.dto.Response;
import inventory.system.dto.SignupRequest;
import inventory.system.security.JwtUtils;
import inventory.system.services.implementation.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {
    private final UserService userService;
    private final JwtUtils jwtUtils;

    @PostMapping("/register")
    public ResponseEntity<Response> registerUser(@RequestBody @Valid SignupRequest signupRequest) {
        return ResponseEntity.ok(userService.registerUser(signupRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<Response> loginUser(
            @RequestBody @Valid LoginRequest loginRequest,
            HttpServletResponse response) {
        Response loginResponse = userService.loginUser(loginRequest);
        // sed refresh token to cookies
        var cookie = new Cookie("refreshToken", loginResponse.getRefreshToken().toString());
        cookie.setHttpOnly(true);
        cookie.setPath("/api/auth/refresh");
        cookie.setMaxAge(86400); // one day
        cookie.setSecure(true);
        response.addCookie(cookie);

        return ResponseEntity.ok(Response.builder()
                .status(200)
                .message("Has iniciado sesión correctamente")
                .role(loginResponse.getRole())
                .token(loginResponse.getToken())
                .build());
    }

    @PostMapping("/refresh")
    public ResponseEntity<JwtResponse> refresh(
            @CookieValue(value = "refreshToken", required = false) String refreshToken) {
        if (refreshToken == null || !jwtUtils.validateRefresh(refreshToken)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        var userEmail = jwtUtils.getUsernameFromToken(refreshToken);
        var user = userService.getUserByEmail(userEmail);
        String token = jwtUtils.generateAccessToken(user.getUser().getEmail());

        return ResponseEntity.ok(new JwtResponse(token));
    }

}
