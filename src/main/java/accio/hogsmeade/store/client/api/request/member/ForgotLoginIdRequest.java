package accio.hogsmeade.store.client.api.request.member;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ForgotLoginIdRequest {

    @NotBlank
    private String email;
}
