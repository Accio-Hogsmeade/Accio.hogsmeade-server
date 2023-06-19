package accio.hogsmeade.store.store.store.service;

import accio.hogsmeade.store.jwt.TokenInfo;
import accio.hogsmeade.store.store.store.Store;
import accio.hogsmeade.store.store.store.repository.StoreRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

import static accio.hogsmeade.store.common.Active.ACTIVE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
class StoreAccountServiceTest {

    @Autowired
    private StoreAccountService storeAccountService;
    @Autowired
    private StoreRepository storeRepository;

    @Test
    @DisplayName("로그인#존재하지 않는 아이디")
    void notExistLoginId(){
        //given

        //when
        String loginId = "loginId";
        String loginPw = "loginPw1234!";

        //then
        assertThatThrownBy(() -> storeAccountService.login(loginId, loginPw))
                .isInstanceOf(BadCredentialsException.class);
    }

    @Test
    @DisplayName("로그인#비밀번호불일치")
    void notEqualLoginPw() {
        //given
        Store store = insertStore();

        //when
        String loginPw = store.getLoginPw() + "!";

        //then
        assertThatThrownBy(() -> storeAccountService.login(store.getLoginId(), loginPw))
                .isInstanceOf(BadCredentialsException.class);
    }

    @Test
    @DisplayName("로그인")
    void login() {
        //given
        Store store = insertStore();

        //when
        TokenInfo tokenInfo = storeAccountService.login(store.getLoginId(), store.getLoginPw());

        //then
        assertThat(tokenInfo).isNotNull();
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
                .roles(Collections.singletonList("STORE"))
                .build();
        return storeRepository.save(store);
    }
}