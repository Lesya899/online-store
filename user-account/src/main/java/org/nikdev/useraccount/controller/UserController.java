package org.nikdev.useraccount.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.nikdev.useraccount.constant.Action;
import org.nikdev.useraccount.dto.request.ActionUserAccountDto;
import org.nikdev.useraccount.dto.request.UserIncomeDto;
import org.nikdev.useraccount.dto.response.UserIncomeOutDto;
import org.nikdev.useraccount.dto.response.UserOutDto;
import org.nikdev.useraccount.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController()
@RequestMapping("/v1/users")
@Tag(name = "ProductController", description = "Действия с пользователями")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    /**
     * Получение пользователя по id
     *
     * @return UserOutDto
     */



    @Operation(summary = "Получение пользователя по id")
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    //@PreAuthorize("hasAnyAuthority('user', 'admin')")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<UserOutDto> getUserById(@PathVariable Integer id) throws Exception {
        UserOutDto userOutDto = userService.findById(id);
        return ResponseEntity.ok(userOutDto);
    }


    /**
     * "Удаление/блокировка/разблокировка аккаунта пользователя"
     *
     * @return String
     */
    @Operation(summary = "Удаление/блокировка/разблокировка аккаунта пользователя")
    @PostMapping(value = "/action", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<String> performAction(@Valid @RequestBody ActionUserAccountDto actionUserAccountDto) throws Exception {
        userService.performAction(actionUserAccountDto);
        String message;
        if (actionUserAccountDto.getAction().equals(Action.DELETE)) {
            message = "User deleted";
        } else if (actionUserAccountDto.getAction().equals(Action.BLOCK)) {
            message = "User is blocked";
        } else {
            message = "User is unblocked";
        }
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    /**
     * Пополнение баланса пользователя
     *
     * @return UserIncomeOutDto
     */
    @Operation(summary = "Пополнение баланса пользователя")
    @PostMapping(value = "/income", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<UserIncomeOutDto> refillBalance(@Valid @RequestBody UserIncomeDto userIncomeDto) throws Exception {
        UserIncomeOutDto userIncomeOutDto = userService.processUserIncome(userIncomeDto);
        return new ResponseEntity<>(userIncomeOutDto, HttpStatus.OK);
    }
}

