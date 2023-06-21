package accio.hogsmeade.store.store.api.request.store;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class LoginStoreRequest {

    @NotBlank
    @ApiModelProperty(example = "storeid")
    private String loginId;
    @NotBlank
    @ApiModelProperty(example = "storepw123!")
    private String loginPw;
}
