package com.example.flyaway.auth.domain;

import com.example.flyaway.auth.dto.LoginRequestDTO;
import com.example.flyaway.auth.dto.TokenResponseDTO;
import com.example.flyaway.auth.security.JwtService;
import com.example.flyaway.common.dto.NewIdDTO;
import com.example.flyaway.common.exception.BadRequestException;
import com.example.flyaway.common.exception.ConflictException;
import com.example.flyaway.user.domain.User;
import com.example.flyaway.user.dto.RegisterUserDTO;
import com.example.flyaway.user.infrastructure.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    public NewIdDTO register(RegisterUserDTO request) {

        if (userRepository.existsByEmail(request.email())) {
            throw new BadRequestException("Email already registered");
        }

        User user = new User(
                request.email(),
                passwordEncoder.encode(request.password()),
                request.firstName(),
                request.lastName()
        );

        User savedUser = userRepository.save(user);

        return new NewIdDTO(savedUser.getId());
    }

    public TokenResponseDTO login(LoginRequestDTO request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                )
        );

        User user = userRepository
                .findByEmail(request.email())
                .orElseThrow();

        String token = jwtService.generateToken(user);

        return new TokenResponseDTO(token);
    }
}
