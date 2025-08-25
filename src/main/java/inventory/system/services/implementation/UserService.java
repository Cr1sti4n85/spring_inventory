package inventory.system.services.implementation;


import inventory.system.dto.LoginRequest;
import inventory.system.dto.Response;
import inventory.system.dto.SignupRequest;
import inventory.system.dto.UserDTO;
import inventory.system.enums.UserRole;
import inventory.system.exceptions.InvalidCredentialsException;
import inventory.system.exceptions.NotFoundException;
import inventory.system.models.User;
import inventory.system.repositories.UserRepository;
import inventory.system.security.JwtUtils;
import inventory.system.services.IUserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    private final JwtUtils jwtUtils;

    @Override
    public Response registerUser(SignupRequest signupRequest) {

        UserRole role = UserRole.MANAGER;

        if (signupRequest.getRole() != null) {
            role = signupRequest.getRole();
        }

        User userToSave = User.builder()
                .name(signupRequest.getName())
                .email(signupRequest.getEmail())
                .password(passwordEncoder.encode(signupRequest.getPassword()))
                .phoneNumber(signupRequest.getPhoneNumber())
                .role(role)
                .build();

        userRepository.save(userToSave);

        return Response.builder()
                .status(200)
                .message("User was successfully registered")
                .build();
    }

    @Override
    public Response loginUser(LoginRequest loginRequest) {
        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new NotFoundException("Email Not Found"));

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException("Password Does Not Match");
        }
        String token = jwtUtils.generateToken(user.getEmail());

        return Response.builder()
                .status(200)
                .message("User Logged in Successfully")
                .role(user.getRole())
                .token(token)
                .expirationTime("6 months")
                .build();
    }

    @Override
    public Response getAllUsers() {
        return null;
    }

    @Override
    public User getCurrentLoggedInUser() {
        return null;
    }

    @Override
    public Response getUserById(Long id) {
        return null;
    }

    @Override
    public Response updateUser(Long id, UserDTO userDTO) {
        return null;
    }

    @Override
    public Response deleteUser(Long id) {
        return null;
    }

    @Override
    public Response getUserTransactions(Long id) {
        return null;
    }

}
