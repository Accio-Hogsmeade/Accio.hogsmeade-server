package accio.hogsmeade.store.client.api.request.member;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class EditAddressRequest {

    @NotBlank
    @Size(max = 10)
    @Pattern(regexp = "^[0-9]*$")
    private String zipcode;
    @NotBlank
    @Size(max = 255)
    private String mainAddress;
    @Size(max = 10)
    private String detailAddress;
}
