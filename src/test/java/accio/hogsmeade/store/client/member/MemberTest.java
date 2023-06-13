package accio.hogsmeade.store.client.member;

import accio.hogsmeade.store.common.Address;
import accio.hogsmeade.store.common.exception.EditException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class MemberTest {

    @Test
    @DisplayName("비밀번호 변경#기존 비밀번호 불일치")
    void no() {
        //given
        Member member = Member.builder()
                .loginPw("harry1234!")
                .build();
        String newLoginPw = "new1234!@";

        //when
        String errorLoginPw = "error1234!";

        //then
        assertThatThrownBy(() -> member.editLoginPw(errorLoginPw, newLoginPw))
                .isInstanceOf(EditException.class);

    }

    @Test
    @DisplayName("비밀번호 변경")
    void editLoginPw() {
        //given
        Member member = Member.builder()
                .loginPw("harry1234!")
                .build();
        String newLoginPw = "new1234!@";

        //when
        member.editLoginPw(member.getLoginPw(), newLoginPw);

        //then
        assertThat(member.getLoginPw()).isEqualTo(newLoginPw);
    }

    @Test
    @DisplayName("연락처 변경")
    void editTel() {
        //given
        Member member = Member.builder()
                .tel("010-1234-1234")
                .build();
        String newTel = "010-5678-5678";

        //when
        member.editTel(newTel);

        //then
        assertThat(member.getTel()).isEqualTo(newTel);
    }

    @Test
    @DisplayName("이메일 변경")
    void editEmail() {
        //given
        Member member = Member.builder()
                .email("harry@naver.com")
                .build();
        String newEmail = "potter@naver.com";

        //when
        member.editEmail(newEmail);

        //then
        assertThat(member.getEmail()).isEqualTo(newEmail);
    }

    @Test
    @DisplayName("주소 변경")
    void editAddress() {
        //given
        Member member = Member.builder()
                .address(Address.builder()
                        .zipcode("12345")
                        .mainAddress("main address")
                        .detailAddress("detail address")
                        .build())
                .build();
        Address newAddress = Address.builder()
                .zipcode("11111")
                .mainAddress("new main address")
                .detailAddress("new detail address")
                .build();

        //when
        member.editAddress(newAddress);

        //then
        assertThat(member.getAddress()).isEqualTo(newAddress);
    }
}