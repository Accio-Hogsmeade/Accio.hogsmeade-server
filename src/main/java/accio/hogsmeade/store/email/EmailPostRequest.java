package accio.hogsmeade.store.email;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class EmailPostRequest {

    @NotBlank
    @Email
    private String email;
}
