package accio.hogsmeade.store.store.store.service.impl;

import accio.hogsmeade.store.store.store.Store;
import accio.hogsmeade.store.store.store.repository.StoreRepository;
import accio.hogsmeade.store.store.store.service.StoreService;
import accio.hogsmeade.store.store.store.service.dto.SignupStoreDto;
import accio.hogsmeade.store.common.exception.DuplicateException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StoreServiceImpl implements StoreService {

    private final StoreRepository storeRepository;

    @Override
    public Long signup(SignupStoreDto dto) {
        Optional<Long> loginId = storeRepository.existLoginId(dto.getLoginId());
        if (loginId.isPresent()) {
            throw new DuplicateException();
        }

        Optional<Long> tel = storeRepository.existTel(dto.getTel());
        if (tel.isPresent()) {
            throw new DuplicateException();
        }

        Optional<Long> email = storeRepository.existEmail(dto.getEmail());
        if (email.isPresent()) {
            throw new DuplicateException();
        }

        Store store = Store.createStore(dto.getLoginId(), dto.getLoginPw(), dto.getShopkeeper(), dto.getTel(), dto.getEmail(), dto.getStoreName(), dto.getStoreInfo());
        Store savedStore = storeRepository.save(store);
        return savedStore.getId();
    }

    @Override
    public Long withdrawal(String loginId, String loginPw) {
        Store findStore = storeRepository.findByLoginId(loginId)
                .orElseThrow(NoSuchElementException::new);

        // TODO: 2023-06-19 상점 탈퇴시 이메일 인증 기능 추가 
        findStore.withdrawal(loginPw);

        return findStore.getId();
    }
}
