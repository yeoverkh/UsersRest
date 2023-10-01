package com.users.yehor_ostroverkhov.controller;

import com.users.yehor_ostroverkhov.dto.ContactsDto;
import com.users.yehor_ostroverkhov.dto.DateRangeDto;
import com.users.yehor_ostroverkhov.dto.UserDto;
import com.users.yehor_ostroverkhov.model.UserEntity;
import com.users.yehor_ostroverkhov.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserEntity>> findAllByBirthBetween(DateRangeDto dateRangeDto) {
        return ResponseEntity.ok(userService.findAllUsersInBirthDateRange(dateRangeDto));
    }

    @PostMapping
    public ResponseEntity<UserEntity> registerUser(@RequestBody @Validated UserDto userDto) {
        UserEntity savedUser = userService.save(userDto);

        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserEntity> changeUserContacts(@PathVariable Long id, @RequestBody ContactsDto contactsDto) {
        UserEntity savedUser = userService.changeUserContacts(id, contactsDto);
        return ResponseEntity.ok(savedUser);
    }

    @PostMapping("/{id}")
    public ResponseEntity<UserEntity> updateAllUserFields(@PathVariable Long id, @RequestBody UserDto userDto) {
        UserEntity savedUser = userService.save(id, userDto);

        return ResponseEntity.ok(savedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<UserEntity> deleteUser(@PathVariable Long id) {
        userService.deleteUserById(id);

        return ResponseEntity.noContent().build();
    }
}
