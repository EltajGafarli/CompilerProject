package com.example.sdpproject.controller.user;

import com.example.sdpproject.dto.auth.request.RegisterRequest;
import com.example.sdpproject.dto.user.UserDto;
import com.example.sdpproject.entity.enums.RoleEnum;
import com.example.sdpproject.service.user.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/admin")
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;

    @PostMapping(path = "/adduser")
    public ResponseEntity<UserDto> addUser(@RequestBody RegisterRequest request) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        adminService.addUser(request)
                );
    }

    @DeleteMapping(path = "/deleteuser")
    public ResponseEntity<String> deleteUser(@RequestParam String email) {
        return ResponseEntity
                .ok(
                        adminService.deleteUser(email)
                );
    }

    @PostMapping(path = "/changeuserrole/{id}")
    public ResponseEntity<String> addUserRole(@PathVariable(name = "id") long userId, @RequestParam RoleEnum roleEnum) {
        return ResponseEntity
                .ok(
                        adminService.addUserRole(userId, roleEnum)
                );
    }

    @DeleteMapping(path = "/deleteuser/{id}")
    public ResponseEntity<String> deleteUserRole(@PathVariable(name = "id") long userId, @RequestParam RoleEnum roleEnum) {
        return ResponseEntity
                .ok(
                        adminService.deleteUserRole(userId, roleEnum)
                );
    }


    @GetMapping(path = "/users")
    public ResponseEntity<List<UserDto>> getUsers() {
        return ResponseEntity
                .ok(
                        adminService.getUsers()
                );
    }


}
