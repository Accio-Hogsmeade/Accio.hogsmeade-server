package accio.hogsmeade.store.client.member.service.impl;

import accio.hogsmeade.store.client.member.Member;
import accio.hogsmeade.store.client.member.repository.MemberRepository;
import accio.hogsmeade.store.client.member.service.MemberService;
import accio.hogsmeade.store.client.member.service.dto.EditAddressDto;
import accio.hogsmeade.store.client.member.service.dto.EditLoginPwDto;
import accio.hogsmeade.store.client.member.service.dto.SignupMemberDto;
import accio.hogsmeade.store.common.exception.DuplicateException;
import accio.hogsmeade.store.common.exception.EditException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    @Override
    public Long signup(SignupMemberDto dto) {
        Optional<Long> loginId = memberRepository.existLoginId(dto.getLoginId());
        if (loginId.isPresent()) {
            throw new DuplicateException();
        }

        Optional<Long> tel = memberRepository.existTel(dto.getTel());
        if (tel.isPresent()) {
            throw new DuplicateException();
        }

        Optional<Long> email = memberRepository.existEmail(dto.getEmail());
        if (email.isPresent()) {
            throw new DuplicateException();
        }

        Member member = Member.createMember(dto.getLoginId(), dto.getLoginPw(), dto.getName(), dto.getTel(), dto.getEmail(), dto.getZipcode(), dto.getMainAddress(), dto.getDetailAddress(), dto.getIdentity(), dto.getSchoolGroup());
        Member savedMember = memberRepository.save(member);
        return savedMember.getId();
    }

    @Override
    public Long withdrawal(String loginId, String loginPw) {
        Member findMember = memberRepository.findByLoginId(loginId)
                .orElseThrow(NoSuchElementException::new);

        findMember.withdrawal(loginPw);

        return findMember.getId();
    }

    @Override
    public Long editLoginPw(String loginId, EditLoginPwDto dto) {
        Member findMember = memberRepository.findByLoginId(loginId)
                .orElseThrow(NoSuchElementException::new);

        findMember.editLoginPw(dto.getNowLoginPw(), dto.getNewLoginPw());

        return findMember.getId();
    }

    @Override
    public Long editTel(String loginId, String newTel) {
        Optional<Member> findMember = memberRepository.findByTel(newTel);
        if (findMember.isPresent()) {
            if (findMember.get().getLoginId().equals(loginId)) {
                throw new EditException();
            }
            throw new DuplicateException();
        }

        Member member = memberRepository.findByLoginId(loginId)
                .orElseThrow(NoSuchElementException::new);
        member.editTel(newTel);
        return member.getId();
    }

    @Override
    public Long editEmail(String loginId, String newEmail) {
        Optional<Member> findMember = memberRepository.findByEmail(newEmail);
        if (findMember.isPresent()) {
            if (findMember.get().getLoginId().equals(loginId)) {
                throw new EditException();
            }
            throw new DuplicateException();
        }

        Member member = memberRepository.findByLoginId(loginId)
                .orElseThrow(NoSuchElementException::new);
        member.editEmail(newEmail);
        return member.getId();
    }

    @Override
    public Long editAddress(String loginId, EditAddressDto dto) {
        return null;
    }
}
