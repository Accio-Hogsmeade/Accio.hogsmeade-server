package accio.hogsmeade.store.store.api.request.store;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class WithdrawalStoreRequest {

    @NotBlank
    @ApiModelProperty(example = "storeid")
    private String loginId;
    @NotBlank
    @ApiModelProperty(example = "harry1234!")
    private String loginPw;
}
