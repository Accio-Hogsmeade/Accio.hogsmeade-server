package accio.hogsmeade.store.client.member.service;

import accio.hogsmeade.store.client.member.service.dto.EditAddressDto;
import accio.hogsmeade.store.client.member.service.dto.EditLoginPwDto;
import accio.hogsmeade.store.client.member.service.dto.SignupMemberDto;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface MemberService {

    Long signup(SignupMemberDto dto);

    Long withdrawal(String loginId, String loginPw);

    Long editLoginPw(String loginId, EditLoginPwDto dto);

    Long editTel(String loginId, String newTel);

    Long editEmail(String loginId, String newEmail);

    Long editAddress(String loginId, EditAddressDto dto);
}
