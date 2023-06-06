package accio.hogsmeade.store.client.api.request.member;

import accio.hogsmeade.store.client.member.Identity;
import accio.hogsmeade.store.client.member.SchoolGroup;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.*;

@Data
public class SignupMemberRequest {

    @NotBlank
    @Size(max = 20)
    @Pattern(regexp = "^[a-zA-Z0-9]*$")
    @ApiModelProperty(example = "harry")
    private String loginId;
    @NotBlank
    @Size(min = 8, max = 20)
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$")
    @ApiModelProperty(example = "harry1234!")
    private String loginPw;
    @NotBlank
    @Size(max = 50)
    @Pattern(regexp = "^[a-zA-Z가-힣]*$")
    @ApiModelProperty(example = "harry potter")
    private String name;
    @NotBlank
    @Size(min = 13, max = 13)
    @Pattern(regexp = "^\\d{3}-\\d{3,4}-\\d{4}$")
    @ApiModelProperty(example = "010-1234-1234")
    private String tel;
    @NotBlank
    @Size(max = 100)
    @Email
    @ApiModelProperty(example = "harry@gmail.com")
    private String email;
    @NotBlank
    @Size(max = 10)
    @Pattern(regexp = "^[0-9]*$")
    @ApiModelProperty(example = "12345")
    private String zipcode;
    @NotBlank
    @Size(max = 255)
    @ApiModelProperty(example = "main address")
    private String mainAddress;
    @Size(max = 10)
    @ApiModelProperty(example = "detail address")
    private String detailAddress;
    @NotNull
    @ApiModelProperty(example = "STUDENT")
    private Identity identity;
    @NotNull
    @ApiModelProperty(example = "GRYFFINDOR")
    private SchoolGroup schoolGroup;
}
