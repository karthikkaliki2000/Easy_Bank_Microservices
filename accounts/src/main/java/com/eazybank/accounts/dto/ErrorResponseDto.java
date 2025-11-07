package com.eazybank.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Schema(
        name = "Error Response",
        description = "Schema to hold error response information"
)
@Data
@AllArgsConstructor
public class ErrorResponseDto {

    @Schema(
            description = "api path invoked by client",
            example = "https://server:port/api/v1/fetch"

    )
    private String apiPath;

    @Schema(
            description = "Error code represent the error happened",
            example = "500"

    )
    private HttpStatus errorCode;

    @Schema(
            description = "Error message representing the error happened",
            example = "Failed due to Internal Server error"

    )
    private String errorMessage;

    @Schema(
            description = "Time representing when the error happened"

    )
    private LocalDateTime errorTime;
}
