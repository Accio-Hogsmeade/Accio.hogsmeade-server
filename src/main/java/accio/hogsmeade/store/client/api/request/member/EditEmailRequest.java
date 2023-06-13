package accio.hogsmeade.store.client.api.request.member;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class EditEmailRequest {

    @NotBlank
    @Size(max = 100)
    @Email
    private String newEmail;
}
