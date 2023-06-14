package accio.hogsmeade.store.store.store.service;

import accio.hogsmeade.store.common.exception.DuplicateException;
import accio.hogsmeade.store.store.store.Store;
import accio.hogsmeade.store.store.store.repository.StoreRepository;
import accio.hogsmeade.store.store.store.service.dto.SignupStoreDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static accio.hogsmeade.store.common.Active.ACTIVE;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@Transactional
class StoreServiceTest {

    @Autowired
    private StoreService storeService;
    @Autowired
    private StoreRepository storeRepository;

    @Test
    @DisplayName("상점 회원가입#아이디 중복")
    void duplicationLoginId() {
        //given
        Store targetStore = insertStore();

        //when
        SignupStoreDto dto = SignupStoreDto.builder()
                .loginId("storeid")
                .build();

        //then
        assertThatThrownBy(() -> storeService.signup(dto))
                .isInstanceOf(DuplicateException.class);
    }

    @Test
    @DisplayName("상점 회원가입#연락처 중복")
    void duplicationTel() {
        //given
        Store targetStore = insertStore();

        //when
        SignupStoreDto dto = SignupStoreDto.builder()
                .tel("010-1234-1234")
                .build();

        //then
        assertThatThrownBy(() -> storeService.signup(dto))
                .isInstanceOf(DuplicateException.class);
    }

    @Test
    @DisplayName("상점 회원가입#이메일 중복")
    void duplicationEmail() {
        //given
        Store targetStore = insertStore();

        //when
        SignupStoreDto dto = SignupStoreDto.builder()
                .email("honney@gmail.com")
                .build();

        //then
        assertThatThrownBy(() -> storeService.signup(dto))
                .isInstanceOf(DuplicateException.class);
    }

    @Test
    @DisplayName("상점 회원가입")
    void signup() {
        //given
        SignupStoreDto dto = SignupStoreDto.builder()
                .loginId("store")
                .loginPw("storePw123!")
                .shopkeeper("store owner")
                .tel("010-1234-5678")
                .email("store@owner.com")
                .storeName("test store")
                .storeInfo("test")
                .build();

        //when
        Long storeId = storeService.signup(dto);

        //then
        Optional<Store> findStore = storeRepository.findById(storeId);
        assertThat(findStore).isPresent();
    }

    private Store insertStore() {
        Store store = Store.builder()
                .loginId("storeid")
                .loginPw("storepw123!")
                .storeName("honeyduke")
                .storeInfo("butter beer")
                .email("honney@gmail.com")
                .tel("010-1234-1234")
                .shopkeeper("ron")
                .active(ACTIVE)
                .build();
        return storeRepository.save(store);
    }
}