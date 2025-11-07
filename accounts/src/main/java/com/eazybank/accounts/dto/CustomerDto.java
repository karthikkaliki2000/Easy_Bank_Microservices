package com.eazybank.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(
        name = "Customer",
        description = "Schema to hold customer and account information"
)
public class CustomerDto {

    @Schema(
            description = "Name of the customer",
            example = "Karthik kaliki"

    )
    @NotEmpty(message = "Name Can not be null or empty")
    @Size(min = 5,max = 30,message = "Name should be in between 5 and 30 characters")
    private String name;


    @Schema(
            description = "Email of the customer",
            example = "karthikkaliki@gmail.com"

    )
    @NotEmpty(message = "Email can not be null or empty")
    @Email(message="Email should be valid")
    private String email;

    @Schema(
            description = "Mobile number of the customer",
            example = "9789678567"

    )
    @Pattern(regexp = "(^$|[0-9]{10})",message = "Mobile Number should be valid and in 10 digits")
    private String mobileNumber;

    private AccountsDto accountsDto;

}
