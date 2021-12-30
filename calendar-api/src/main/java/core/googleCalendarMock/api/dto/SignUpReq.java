package core.googleCalendarMock.api.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
public class SignUpReq {
    @NotBlank
    private final String name;

    @Email
    @NotBlank
    private final String email;

    @Size(min=6, message="at least 6 characters")
    @NotBlank
    private final String password;

    @NotNull
    private final LocalDate birthday;
}
