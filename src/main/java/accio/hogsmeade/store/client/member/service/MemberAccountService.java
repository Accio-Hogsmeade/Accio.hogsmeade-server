package accio.hogsmeade.store.client.member.service;

import accio.hogsmeade.store.jwt.TokenInfo;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface MemberAccountService {

    TokenInfo login(String loginId, String loginPw);

    String forgotLoginId(String email);
}
