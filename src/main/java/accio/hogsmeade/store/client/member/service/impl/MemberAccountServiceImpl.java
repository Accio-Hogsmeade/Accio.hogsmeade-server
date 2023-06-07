package accio.hogsmeade.store.client.member.service.impl;

import accio.hogsmeade.store.client.member.service.MemberAccountService;
import accio.hogsmeade.store.jwt.TokenInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberAccountServiceImpl implements MemberAccountService {

    @Override
    public TokenInfo login(String loginId, String loginPw) {
        return null;
    }
}
