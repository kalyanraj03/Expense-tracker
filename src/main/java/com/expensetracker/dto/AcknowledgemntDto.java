package com.expensetracker.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AcknowledgemntDto {

    private String error;

    private String status;

    private LocalDateTime registeredAt;

}
