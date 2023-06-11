package accio.hogsmeade.store.client.member.service;

import accio.hogsmeade.store.client.member.service.dto.EditLoginPwDto;
import accio.hogsmeade.store.client.member.service.dto.SignupMemberDto;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface MemberService {

    Long signup(SignupMemberDto dto);

    Long withdrawal(String loginId, String loginPw);

    Long editLoginPw(String loginId, EditLoginPwDto dto);
}
