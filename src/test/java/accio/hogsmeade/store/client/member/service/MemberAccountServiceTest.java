package accio.hogsmeade.store.client.member.service;

import accio.hogsmeade.store.client.member.Member;
import accio.hogsmeade.store.client.member.repository.MemberRepository;
import accio.hogsmeade.store.common.Address;
import accio.hogsmeade.store.jwt.TokenInfo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.NoSuchElementException;

import static accio.hogsmeade.store.client.member.Identity.STUDENT;
import static accio.hogsmeade.store.client.member.SchoolGroup.GRYFFINDOR;
import static accio.hogsmeade.store.common.Active.ACTIVE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
class MemberAccountServiceTest {

    @Autowired
    private MemberAccountService memberAccountService;
    @Autowired
    private MemberRepository memberRepository;

    @Test
    @DisplayName("로그인#존재하지않은아이디")
    void notExistLoginId() {
        //given

        //when
        String loginId = "loginId";
        String loginPw = "loginPw1234!";

        //then
        assertThatThrownBy(() -> memberAccountService.login(loginId, loginPw))
                .isInstanceOf(BadCredentialsException.class);
    }

    @Test
    @DisplayName("로그인#비밀번호불일치")
    void notEqualLoginPw() {
        //given
        Member member = insertMember();

        //when
        String loginPw = member.getLoginPw() + "!";

        //then
        assertThatThrownBy(() -> memberAccountService.login(member.getLoginId(), loginPw))
                .isInstanceOf(BadCredentialsException.class);
    }

    @Test
    @DisplayName("로그인")
    void login() {
        //given
        Member member = insertMember();

        //when
        TokenInfo tokenInfo = memberAccountService.login(member.getLoginId(), member.getLoginPw());

        //then
        assertThat(tokenInfo).isNotNull();
    }

    @Test
    @DisplayName("아이디 찾기#존재하지 않는 계정")
    void notExistAccountByLoginId() {
        //given
        String email = "empty@naver.com";

        //when

        //then
        assertThatThrownBy(() -> memberAccountService.forgotLoginId(email))
                .isInstanceOf(NoSuchElementException.class);

    }

    @Test
    @DisplayName("아이디 찾기")
    void forgotLoginId() {
        //given
        Member member = insertMember();

        //when
        String loginId = memberAccountService.forgotLoginId(member.getEmail());

        //then
        assertThat(loginId).isEqualTo(member.getLoginId());
    }

    private Member insertMember() {
        Member member = Member.builder()
                .loginId("harry")
                .loginPw("harry1234!")
                .name("harry potter")
                .tel("010-1234-1234")
                .email("harry@naver.com")
                .address(Address.builder()
                        .zipcode("12345")
                        .mainAddress("main address")
                        .detailAddress("detail address")
                        .build())
                .identity(STUDENT)
                .schoolGroup(GRYFFINDOR)
                .active(ACTIVE)
                .roles(Collections.singletonList("MEMBER"))
                .build();
        return memberRepository.save(member);
    }
}