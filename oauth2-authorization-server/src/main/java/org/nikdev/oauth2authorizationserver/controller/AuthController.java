package org.nikdev.oauth2authorizationserver.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.nikdev.oauth2authorizationserver.dto.UserCreateDto;
import org.nikdev.oauth2authorizationserver.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/v1/auth")
@Tag(name = "AuthController", description = "Действия с пользователем")
@RequiredArgsConstructor
public class AuthController {


    private final UserService userService;


    /**
     * Регистрация пользователя
     *
     */
    @Operation(summary = "Регистрация пользователя")
    @PostMapping("/registration")
    public ResponseEntity<Void> registrationUser(@RequestBody UserCreateDto userCreateDto) throws Exception {
        userService.registrationUser(userCreateDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
