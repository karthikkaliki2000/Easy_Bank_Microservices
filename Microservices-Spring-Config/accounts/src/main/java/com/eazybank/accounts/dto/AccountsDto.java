package com.eazybank.accounts.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Schema(
        description = "Schema to hold Account details information",
        name = "Accounts"


)
@Data
public class AccountsDto {

    @Schema(
            description = "Account number of eazy bank account"

    )
    @NotEmpty(message = "Account number should not be null or empty")
    @Pattern(regexp = "(^$|[0-9]{10})",message = "Account Number should be valid and in 10 digits")
    private Long accountNumber;


    @Schema(
            description = "Account type of eazy bank account",
            example = "Savings"

    )
    @NotEmpty(message = "Account type should not be null or empty")
    private String accountType;


    @Schema(
            description = "Branch address of eazy bank account",
            example = "123 Newyork"

    )
    @NotEmpty(message = "Branch address should not be null or empty")
    private String branchAddress;
}