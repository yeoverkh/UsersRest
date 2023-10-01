package com.users.yehor_ostroverkhov.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record DateRangeDto(LocalDate fromDate, LocalDate toDate) {

    public DateRangeDto(@NotNull LocalDate fromDate, @NotNull LocalDate toDate) {
        if (fromDate.isBefore(toDate)) {
            this.fromDate = fromDate;
            this.toDate = toDate;
        } else {
            throw new IllegalArgumentException();
        }
    }
}
