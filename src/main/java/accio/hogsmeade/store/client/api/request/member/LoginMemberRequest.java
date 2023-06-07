package accio.hogsmeade.store.client.api.request.member;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class LoginMemberRequest {

    @NotBlank
    @ApiModelProperty(example = "harry")
    private String loginId;
    @NotBlank
    @ApiModelProperty(example = "harry1234!")
    private String loginPw;
}
