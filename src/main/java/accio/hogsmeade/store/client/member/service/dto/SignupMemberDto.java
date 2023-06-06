package accio.hogsmeade.store.client.member.service.dto;

import accio.hogsmeade.store.client.member.Identity;
import accio.hogsmeade.store.client.member.SchoolGroup;
import lombok.Builder;
import lombok.Data;

@Data
public class SignupMemberDto {

    private String loginId;
    private String loginPw;
    private String name;
    private String tel;
    private String email;
    private String zipcode;
    private String mainAddress;
    private String detailAddress;
    private Identity identity;
    private SchoolGroup schoolGroup;

    @Builder
    public SignupMemberDto(String loginId, String loginPw, String name, String tel, String email, String zipcode, String mainAddress, String detailAddress, Identity identity, SchoolGroup schoolGroup) {
        this.loginId = loginId;
        this.loginPw = loginPw;
        this.name = name;
        this.tel = tel;
        this.email = email;
        this.zipcode = zipcode;
        this.mainAddress = mainAddress;
        this.detailAddress = detailAddress;
        this.identity = identity;
        this.schoolGroup = schoolGroup;
    }
}
