package accio.hogsmeade.store.store.store.service;

import accio.hogsmeade.store.store.store.service.dto.SignupStoreDto;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface StoreService {

    Long signup(SignupStoreDto dto);

    Long withdrawal(String loginId, String loginPw);
}
