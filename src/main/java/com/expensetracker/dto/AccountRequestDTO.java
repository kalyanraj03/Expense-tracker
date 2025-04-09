package com.expensetracker.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class AccountRequestDTO {

    @NotBlank(message = "Account name is required")
    @Size(min = 3, max = 50, message = "Account name must be between 3 and 50 characters")
    private String accountName;

    @NotBlank(message = "Account type is required")
    @Pattern(
            regexp = "^(SAVINGS|CASH|UPI|CUSTOM)$",
            message = "Account type must be one of: SAVINGS, CASH, UPI, CUSTOM"
    )
    private String accountType;

    @NotNull(message = "Balance is required")
    @DecimalMin(value = "0.0", inclusive = true, message = "Balance must be 0 or more")
    private BigDecimal balance;
}
