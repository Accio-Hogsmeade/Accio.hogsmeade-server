package accio.hogsmeade.store.client.member.service;

import accio.hogsmeade.store.client.member.Member;
import accio.hogsmeade.store.client.member.repository.MemberRepository;
import accio.hogsmeade.store.client.member.service.dto.EditAddressDto;
import accio.hogsmeade.store.client.member.service.dto.EditLoginPwDto;
import accio.hogsmeade.store.client.member.service.dto.SignupMemberDto;
import accio.hogsmeade.store.common.Address;
import accio.hogsmeade.store.common.exception.AuthorityException;
import accio.hogsmeade.store.common.exception.DuplicateException;
import accio.hogsmeade.store.common.exception.EditException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Optional;

import static accio.hogsmeade.store.client.member.Identity.STUDENT;
import static accio.hogsmeade.store.client.member.SchoolGroup.GRYFFINDOR;
import static accio.hogsmeade.store.common.Active.ACTIVE;
import static accio.hogsmeade.store.common.Active.DEACTIVE;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    private MemberService memberService;
    @Autowired
    private MemberRepository memberRepository;

    @Test
    @DisplayName("회원가입#아이디 중복")
    void duplicationLoginId() {
        //given
        Member targetMember = insertMember();

        //when
        SignupMemberDto dto = SignupMemberDto.builder()
                .loginId("harry")
                .build();

        //then
        assertThatThrownBy(() -> memberService.signup(dto))
                .isInstanceOf(DuplicateException.class);
    }

    @Test
    @DisplayName("회원가입#연락처 중복")
    void duplicationTel() {
        //given
        Member targetMember = insertMember();

        //when
        SignupMemberDto dto = SignupMemberDto.builder()
                .tel("010-1234-1234")
                .build();

        //then
        assertThatThrownBy(() -> memberService.signup(dto))
                .isInstanceOf(DuplicateException.class);
    }

    @Test
    @DisplayName("회원가입#이메일 중복")
    void duplicationEmail() {
        //given
        Member targetMember = insertMember();

        //when
        SignupMemberDto dto = SignupMemberDto.builder()
                .email("harry@naver.com")
                .build();

        //then
        assertThatThrownBy(() -> memberService.signup(dto))
                .isInstanceOf(DuplicateException.class);
    }

    @Test
    @DisplayName("회원가입")
    void signup() {
        //given
        SignupMemberDto dto = SignupMemberDto.builder()
                .loginId("harry")
                .loginPw("harry1234!")
                .name("harry potter")
                .tel("010-1234-1234")
                .email("harry@naver.com")
                .zipcode("12345")
                .mainAddress("main address")
                .detailAddress("detail address")
                .identity(STUDENT)
                .schoolGroup(GRYFFINDOR)
                .build();

        //when
        Long memberId = memberService.signup(dto);

        //then
        Optional<Member> findMember = memberRepository.findById(memberId);
        assertThat(findMember).isPresent();
    }

    @Test
    @DisplayName("회원탈퇴#비밀번호 불일치")
    void notEqualLoginPw() {
        //given
        Member targetMember = insertMember();

        //when
        String loginPw = targetMember.getLoginPw() + "@";

        //then
        assertThatThrownBy(() -> memberService.withdrawal(targetMember.getLoginId(), loginPw))
                .isInstanceOf(AuthorityException.class);
    }

    @Test
    @DisplayName("회원탈퇴#존재하지 않는 회원")
    void notExistMember() {
        //given

        //when
        String loginId = "abcde";
        String loginPw = "abc1234!";

        //then
        assertThatThrownBy(() -> memberService.withdrawal(loginId, loginPw))
                .isInstanceOf(NoSuchElementException.class);

    }

    @Test
    @DisplayName("회원탈퇴")
    void withdrawal() {
        //given
        Member targetMember = insertMember();

        //when
        Long memberId = memberService.withdrawal(targetMember.getLoginId(), targetMember.getLoginPw());

        //then
        Optional<Member> findMember = memberRepository.findById(memberId);
        assertThat(findMember).isPresent();
        assertThat(findMember.get().getActive()).isEqualTo(DEACTIVE);
    }

    @Test
    @DisplayName("비밀번호 수정")
    void editLoginPw() {
        //given
        Member member = insertMember();
        String newLoginPw = member.getLoginPw() + "@";
        EditLoginPwDto dto = EditLoginPwDto.builder()
                .nowLoginPw(member.getLoginPw())
                .newLoginPw(newLoginPw)
                .build();

        //when
        Long memberId = memberService.editLoginPw(member.getLoginId(), dto);

        //then
        Optional<Member> findMember = memberRepository.findById(memberId);
        assertThat(findMember).isPresent();
        assertThat(findMember.get().getLoginPw()).isEqualTo(newLoginPw);
    }

    @Test
    @DisplayName("연락처 변경#연락처 중복")
    void duplicationNewTel() {
        //given
        Member member = insertMember();
        Member targetMember = Member.builder()
                .loginId("harry1")
                .loginPw("harry1234!")
                .name("harry potter")
                .tel("010-5678-5678")
                .email("harry1@naver.com")
                .address(Address.builder()
                        .zipcode("12345")
                        .mainAddress("main address")
                        .detailAddress("detail address")
                        .build())
                .identity(STUDENT)
                .schoolGroup(GRYFFINDOR)
                .active(ACTIVE)
                .build();
        memberRepository.save(targetMember);

        //when

        //then
        assertThatThrownBy(() -> memberService.editTel(member.getLoginId(), targetMember.getTel()))
                .isInstanceOf(DuplicateException.class);
    }

    @Test
    @DisplayName("연락처 변경#기존 연락처와 동일")
    void equalNowTel() {
        //given
        Member member = insertMember();

        //when

        //then
        assertThatThrownBy(() -> memberService.editTel(member.getLoginId(), member.getTel()))
                .isInstanceOf(EditException.class);
    }

    @Test
    @DisplayName("연락처 변경")
    void editTel() {
        //given
        Member member = insertMember();
        String newTel = "010-5678-5678";

        //when
        Long memberId = memberService.editTel(member.getLoginId(), newTel);

        //then
        Optional<Member> findMember = memberRepository.findById(memberId);
        assertThat(findMember).isPresent();
        assertThat(findMember.get().getTel()).isEqualTo(newTel);
    }

    @Test
    @DisplayName("이메일 변경#이메일 중복")
    void duplicationNewEmail() {
        //given
        Member member = insertMember();
        Member targetMember = Member.builder()
                .loginId("harry1")
                .loginPw("harry1234!")
                .name("harry potter")
                .tel("010-5678-5678")
                .email("potter@naver.com")
                .address(Address.builder()
                        .zipcode("12345")
                        .mainAddress("main address")
                        .detailAddress("detail address")
                        .build())
                .identity(STUDENT)
                .schoolGroup(GRYFFINDOR)
                .active(ACTIVE)
                .build();
        memberRepository.save(targetMember);

        //when

        //then
        assertThatThrownBy(() -> memberService.editEmail(member.getLoginId(), targetMember.getEmail()))
                .isInstanceOf(DuplicateException.class);
    }

    @Test
    @DisplayName("이메일 변경#기존 이메일과 동일")
    void equalNowEmail() {
        //given
        Member member = insertMember();

        //when

        //then
        assertThatThrownBy(() -> memberService.editEmail(member.getLoginId(), member.getEmail()))
                .isInstanceOf(EditException.class);
    }

    @Test
    @DisplayName("이메일 변경")
    void editEmail() {
        //given
        Member member = insertMember();
        String newEmail = "potter@naver.com";

        //when
        Long memberId = memberService.editEmail(member.getLoginId(), newEmail);

        //then
        Optional<Member> findMember = memberRepository.findById(memberId);
        assertThat(findMember).isPresent();
        assertThat(findMember.get().getEmail()).isEqualTo(newEmail);
    }

    @Test
    @DisplayName("주소 변경")
    void editAddress() {
        //given
        Member member = insertMember();
        EditAddressDto newAddress = EditAddressDto.builder()
                .zipcode("11111")
                .mainAddress("new main address")
                .detailAddress("new detail address")
                .build();

        //when
        Long memberId = memberService.editAddress(member.getLoginId(), newAddress);

        //then
        Optional<Member> findMember = memberRepository.findById(memberId);
        assertThat(findMember).isPresent();
        assertThat(findMember.get().getAddress().getZipcode()).isEqualTo(newAddress.getZipcode());
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
                .build();
        return memberRepository.save(member);
    }
}