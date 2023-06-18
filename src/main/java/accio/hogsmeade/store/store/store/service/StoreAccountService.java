package accio.hogsmeade.store.store.store.service;

import accio.hogsmeade.store.jwt.TokenInfo;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface StoreAccountService {

    TokenInfo login(String loginId, String loginPw);
}
