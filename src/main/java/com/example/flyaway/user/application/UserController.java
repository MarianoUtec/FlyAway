package com.example.flyaway.user.application;

import com.example.flyaway.auth.domain.AuthService;
import com.example.flyaway.common.dto.NewIdDTO;
import com.example.flyaway.user.dto.RegisterUserDTO;
import com.example.flyaway.user.dto.UserInfoDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<NewIdDTO> register(
            @Valid @RequestBody RegisterUserDTO request
    ) {

        return ResponseEntity.status(201).body(
                authService.register(request)
        );
    }

    @GetMapping("/me")
    public ResponseEntity<UserInfoDTO> me() {

        Authentication auth =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        UserDetails principal =
                (UserDetails) auth.getPrincipal();

        return ResponseEntity.ok(
                new UserInfoDTO(
                        principal.getUsername()
                )
        );
    }
}
