package ru.test.tplatfom.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.test.tplatfom.exceptions.auth.UsernameExistsException;
import ru.test.tplatfom.exceptions.role.RoleNotFoundException;
import ru.test.tplatfom.DTO.auth.AuthDTO;
import ru.test.tplatfom.DTO.auth.RegistrationDTO;
import ru.test.tplatfom.DTO.test.TestDTO;
import ru.test.tplatfom.DTO.test.TestResultDTO;
import ru.test.tplatfom.DTO.user.UserDTO;
import ru.test.tplatfom.DTO.user.UserShortDTO;
import ru.test.tplatfom.domain.Role;
import ru.test.tplatfom.domain.TestResult;
import ru.test.tplatfom.domain.User;
import ru.test.tplatfom.exceptions.user.UserNotFoundException;
import ru.test.tplatfom.mappers.TestMapper;
import ru.test.tplatfom.mappers.TestResultMapper;
import ru.test.tplatfom.mappers.UserMapper;
import ru.test.tplatfom.repos.RoleRepository;
import ru.test.tplatfom.repos.UserRepository;
import ru.test.tplatfom.requests.auth.AuthRequest;
import ru.test.tplatfom.requests.auth.RegistrationRequest;
import ru.test.tplatfom.requests.user.RoleUpdateRequest;
import ru.test.tplatfom.requests.user.UserDeleteRequest;
import ru.test.tplatfom.requests.user.UserUpdateByAdminRequest;
import ru.test.tplatfom.requests.user.UserUpdateRequest;
import ru.test.tplatfom.security.JwtProvider;

import java.security.Principal;
import java.util.List;
import java.util.Set;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private RoleRepository roleRepository;

    public AuthDTO authUser(AuthRequest request) {
        User user = userRepo.findByUsername(request.getUsername());

        if (user == null) {
            throw new UserNotFoundException("Username is invalid");

        }

        if(!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new UserNotFoundException("Password is invalid");
        }

        String token = jwtProvider.generateToken(user.getUsername());
        return new AuthDTO(user.getUsername(),
                token,
                "OK");
    }

    public RegistrationDTO registerUser(RegistrationRequest registrationRequest) {
        boolean isExists = userRepo.findByUsername(registrationRequest.getUsername()) != null;

        if (isExists) {
            throw new UsernameExistsException("Username is already taken");
        }

        Role role = roleRepository.findByName("Student");

        User user = new User();
        user.setUsername(registrationRequest.getUsername());
        user.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
        user.addRole(role);
        role.addUser(user);

        roleRepository.save(role);
        userRepo.save(user);

        return new RegistrationDTO("OK");
    }

    public Set<UserShortDTO> getAllUsers() {
        List<User> users = userRepo.findAll();

        return UserMapper.fromUsersToShortDTOs(users);
    }

    public UserDTO getUser(Long id) {
        return UserMapper.fromUserToDTO(userRepo.findById(id).orElseThrow(() -> new UserNotFoundException(id)));
    }

    public UserDTO updateUser(Long id, UserUpdateRequest request) {
        User user = userRepo.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());
        userRepo.save(user);
        return UserMapper.fromUserToDTO(user);
    }

    public UserDTO updateUserByAdmin(Long id, UserUpdateByAdminRequest request) {
        User user = userRepo.findById(id).orElseThrow(() -> new UserNotFoundException(id));

        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        request.getRoles()
                .forEach((role -> {
                    if (roleRepository.findByName(role.getName()) == null) {
                        throw new RoleNotFoundException("Role is not correct");
                    }
                }));


        for (RoleUpdateRequest role : request.getRoles()) {
            Role role_entity = roleRepository.findByName(role.getName());
            user.addRole(role_entity);
            role_entity.addUser(user);
        }

        userRepo.save(user);

        return UserMapper.fromUserToDTO(user);
    }

    public void deleteUser(UserDeleteRequest request) {
        userRepo.delete(userRepo.findById(request.getId()).orElseThrow(
                () -> new UserNotFoundException(request.getId())));
    }

    public List<TestDTO> getUserTests(Principal principal) {
        User user = userRepo.findByUsername(principal.getName());

        if (user == null) {
            throw new UserNotFoundException("User is incorrect");
        }

        return TestMapper.fromTestsToDTOs(List.copyOf(user.getTests()));
    }

    public List<TestResultDTO> getUserResults(Long id) {
        List<TestResult> results = List.copyOf(userRepo.findById(id).orElseThrow(
                () -> new UserNotFoundException(id)
        ).getTestResults());

        return TestResultMapper.fromTestsResultsToDTOs(results);
    }

    public List<TestResultDTO> getUserResults(Principal principal) {
        User user = userRepo.findByUsername(principal.getName());

        if (user == null) {
            throw new UserNotFoundException("User is incorrect");
        }

        return TestResultMapper.fromTestsResultsToDTOs(List.copyOf(user.getTestResults()));
    }

    public UserDTO getProfile(Principal principal) {
        User user = userRepo.findByUsername(principal.getName());

        if (user == null) {
            throw new UserNotFoundException("User is incorrect");
        }

        return UserMapper.fromUserToDTO(user);
    }

    public UserDTO updateUtil(Principal principal, UserUpdateRequest request) {
        User user = userRepo.findByUsername(principal.getName());

        if (user == null) {
            throw new UserNotFoundException("User is incorrect");
        }

        return updateUser(user.getId(), request);
    }
}
