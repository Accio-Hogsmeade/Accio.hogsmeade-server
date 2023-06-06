package accio.hogsmeade.store.client.member.service.impl;

import accio.hogsmeade.store.client.member.repository.MemberRepository;
import accio.hogsmeade.store.client.member.service.MemberService;
import accio.hogsmeade.store.client.member.service.dto.SignupMemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    @Override
    public Long signup(SignupMemberDto dto) {
        return null;
    }
}
