package inventory.system.services;

import inventory.system.dto.LoginRequest;
import inventory.system.dto.Response;
import inventory.system.dto.SignupRequest;
import inventory.system.dto.UserDTO;
import inventory.system.models.User;

public interface IUserService {

    Response registerUser(SignupRequest signupRequest);

    Response loginUser(LoginRequest loginRequest);

    Response getAllUsers();

    User getCurrentLoggedInUser();

    Response getUserById(Long id);

    Response getUserByEmail(String email);

    Response updateUser(Long id, UserDTO userDTO);

    Response deleteUser(Long id);

    Response getUserTransactions(Long id);
}
