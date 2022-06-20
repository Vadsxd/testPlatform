package ru.test.tplatfom.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.test.tplatfom.DTO.user.UserDTO;
import ru.test.tplatfom.DTO.user.UserShortDTO;
import ru.test.tplatfom.exceptions.auth.UnauthorizedException;
import ru.test.tplatfom.requests.user.UserDeleteRequest;
import ru.test.tplatfom.requests.user.UserUpdateByAdminRequest;
import ru.test.tplatfom.requests.user.UserUpdateRequest;
import ru.test.tplatfom.services.UserService;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Optional;
import java.util.Set;

@RestController()
@RequestMapping("/api/users/")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    @PreAuthorize("hasAuthority('Admin')")
    public ResponseEntity<Set<UserShortDTO>> getAllUsers() {
        return ResponseEntity.of(Optional.of(userService.getAllUsers()));
    }

    @PreAuthorize("hasAnyAuthority('Admin', 'Teacher')")
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable Long id) {
        return ResponseEntity.of(Optional.of(userService.getUser(id)));
    }

    @PreAuthorize("hasAuthority('Admin')")
    @PostMapping("/{id}")
    public ResponseEntity<UserDTO> updateUserByAdmin(
            @PathVariable Long id,
            @RequestBody @Valid UserUpdateByAdminRequest request
    ) {
        return ResponseEntity.of(Optional.of(userService.updateUserByAdmin(id, request)));
    }

    @PreAuthorize("hasAuthority('Admin')")
    @DeleteMapping
    public void deleteUser(@RequestBody @Valid UserDeleteRequest request) {
        userService.deleteUser(request);
    }


    @GetMapping("/profile")
    public ResponseEntity<UserDTO> getProfile(Principal principal) {
        if (principal == null) {
            throw new UnauthorizedException();
        }
        return ResponseEntity.of(Optional.of(userService.getProfile(principal)));
    }

    @PostMapping("/profile/edit")
    public ResponseEntity<UserDTO> updateUser(@RequestBody @Valid UserUpdateRequest request, Principal principal) {
        if (principal == null) {
            throw new UnauthorizedException();
        }
        return ResponseEntity.of(Optional.of(userService.updateUtil(principal, request)));
    }
//    По факту ненужные эндпоинты так как всю это информацию можно получить по /api/user/{id} (GET)
//
//    @PreAuthorize("hasAuthority('Teacher')")
//    @GetMapping("/test/")
//    public ResponseEntity<List<TestDTO>> getUserTests(Principal principal) {
//        return ResponseEntity.of(Optional.of(userService.getUserTests(principal)));
//    }

//    @PreAuthorize("hasAuthority('Teacher')")
//    @GetMapping("/{id}/testresults")
//    public ResponseEntity<List<TestResultDTO>> getUserResultsByTeacher(@PathVariable Long id) {
//        return ResponseEntity.of(Optional.of(userService.getUserResults(id)));
//    }

//    @GetMapping("/testresults")
//    public ResponseEntity<List<TestResultDTO>> getUserResults(Principal principal) {
//        if (principal == null) {
//            throw new UnauthorizedException();
//        }
//        return ResponseEntity.of(Optional.of(userService.getUserResults(principal)));
//    }
}
