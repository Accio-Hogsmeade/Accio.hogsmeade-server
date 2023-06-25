package accio.hogsmeade.store.store.store.service;

import accio.hogsmeade.store.common.exception.AuthorityException;
import accio.hogsmeade.store.common.exception.DuplicateException;
import accio.hogsmeade.store.common.exception.EditException;
import accio.hogsmeade.store.store.store.Store;
import accio.hogsmeade.store.store.store.repository.StoreRepository;
import accio.hogsmeade.store.store.store.service.dto.SignupStoreDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Optional;

import static accio.hogsmeade.store.common.Active.ACTIVE;
import static accio.hogsmeade.store.common.Active.DEACTIVE;
import static org.assertj.core.api.Assertions.*;

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

    @Test
    @DisplayName("상점회원탈퇴#비밀번호 불일치")
    void notEqualLoginPw() {
        //given
        Store targetStore = insertStore();

        //when
        String loginPw = targetStore.getLoginPw() + "@";

        //then
        assertThatThrownBy(() -> storeService.withdrawal(targetStore.getLoginId(), loginPw))
                .isInstanceOf(AuthorityException.class);
    }

    @Test
    @DisplayName("상점회원탈퇴#존재하지 않는 회원")
    void notExistStore() {
        //given

        //when
        String loginId = "abcde";
        String loginPw = "abc1234!";

        //then
        assertThatThrownBy(() -> storeService.withdrawal(loginId, loginPw))
                .isInstanceOf(NoSuchElementException.class);

    }

    @Test
    @DisplayName("상점회원탈퇴")
    void withdrawal() {
        //given
        Store targetStore = insertStore();

        //when
        Long storeId = storeService.withdrawal(targetStore.getLoginId(), targetStore.getLoginPw());

        //then
        Optional<Store> findStore = storeRepository.findById(storeId);
        assertThat(findStore).isPresent();
        assertThat(findStore.get().getActive()).isEqualTo(DEACTIVE);
    }

    @Test
    @DisplayName("비밀번호 변경")
    void editLoginPw() {
        //given
        Store store = insertStore();
        String newLoginPw = store.getLoginPw() + "@";

        //when
        Long storeId = storeService.editLoginPw(store.getLoginId(), store.getLoginPw(),newLoginPw);

        //then
        Optional<Store> findStore = storeRepository.findById(storeId);
        assertThat(findStore).isPresent();
        assertThat(findStore.get().getLoginPw()).isEqualTo(newLoginPw);
    }

    @Test
    @DisplayName("연락처 변경#연락처 중복")
    void duplicationNewTel() {
        //given
        Store store = insertStore();
        Store targetStore = Store.builder()
                .loginId("Dstoreid")
                .loginPw("Dstorepw123!")
                .storeName("Dhoneyduke")
                .storeInfo("Dbutter beer")
                .email("Dhonney@gmail.com")
                .tel("010-1234-5678")
                .shopkeeper("Dron")
                .active(ACTIVE)
                .build();
        storeRepository.save(targetStore);

        //when

        //then
        assertThatThrownBy(() -> storeService.editTel(store.getLoginId(), targetStore.getTel()))
                .isInstanceOf(DuplicateException.class);
    }

    @Test
    @DisplayName("연락처 변경#기존 연락처와 동일")
    void equalNowTel() {
        //given
        Store store = insertStore();

        //when

        //then
        assertThatThrownBy(() -> storeService.editTel(store.getLoginId(), store.getTel()))
                .isInstanceOf(EditException.class);
    }

    @Test
    @DisplayName("연락처 변경")
    void editTel() {
        //given
        Store store = insertStore();
        String newTel = "010-5678-5678";

        //when
        Long storeId = storeService.editTel(store.getLoginId(), newTel);

        //then
        Optional<Store> findStore = storeRepository.findById(storeId);
        Assertions.assertThat(findStore).isPresent();
        Assertions.assertThat(findStore.get().getTel()).isEqualTo(newTel);
    }

    @Test
    @DisplayName("이메일 변경#이메일 중복")
    void duplicationNewEmail() {
        //given
        Store store = insertStore();
        Store targetStore = Store.builder()
                .loginId("Dstoreid")
                .loginPw("Dstorepw123!")
                .storeName("Dhoneyduke")
                .storeInfo("Dbutter beer")
                .email("honney1@gmail.com")
                .tel("010-1234-2222")
                .shopkeeper("Dron")
                .active(ACTIVE)
                .build();;
        storeRepository.save(targetStore);

        //when

        //then
        assertThatThrownBy(() -> storeService.editEmail(store.getLoginId(), targetStore.getEmail()))
                .isInstanceOf(DuplicateException.class);
    }

    @Test
    @DisplayName("이메일 변경#기존 이메일과 동일")
    void equalNowEmail() {
        //given
        Store store = insertStore();

        //when

        //then
        assertThatThrownBy(() -> storeService.editEmail(store.getLoginId(), store.getEmail()))
                .isInstanceOf(EditException.class);
    }

    @Test
    @DisplayName("이메일 변경")
    void editEmail() {
        //given
        Store store = insertStore();
        String newEmail = "potter@naver.com";

        //when
        Long storeId = storeService.editEmail(store.getLoginId(), newEmail);

        //then
        Optional<Store> findStore = storeRepository.findById(storeId);
        Assertions.assertThat(findStore).isPresent();
        Assertions.assertThat(findStore.get().getEmail()).isEqualTo(newEmail);
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