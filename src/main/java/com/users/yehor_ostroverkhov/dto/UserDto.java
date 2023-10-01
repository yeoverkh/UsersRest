package com.users.yehor_ostroverkhov.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record UserDto(@NotNull String email, @NotNull String firstName, @NotNull String lastName,
                      @NotNull LocalDate birthDate, String address, String phoneNumber) {
}
