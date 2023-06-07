package accio.hogsmeade.store.client.api;

import accio.hogsmeade.store.client.api.request.member.SignupMemberRequest;
import accio.hogsmeade.store.client.api.request.member.WithdrawalMemberRequest;
import accio.hogsmeade.store.client.member.service.MemberService;
import accio.hogsmeade.store.client.member.service.dto.SignupMemberDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@Slf4j
@RequiredArgsConstructor
@Api(tags = {"회원계정"})
public class AccountApiController {

    private final MemberService memberService;

    @ApiOperation(value = "회원가입")
    @PostMapping("/signup")
    public Long signup(@RequestBody SignupMemberRequest request) {
        log.debug("SignupMemberRequest={}", request);
        SignupMemberDto dto = SignupMemberDto.builder()
                .loginId(request.getLoginId())
                .loginPw(request.getLoginPw())
                .name(request.getName())
                .tel(request.getTel())
                .email(request.getEmail())
                .zipcode(request.getZipcode())
                .mainAddress(request.getMainAddress())
                .detailAddress(request.getDetailAddress())
                .identity(request.getIdentity())
                .schoolGroup(request.getSchoolGroup())
                .build();

        Long memberId = memberService.signup(dto);
        log.info("signup member={}", memberId);
        return memberId;
    }

    @ApiOperation(value = "회원탈퇴")
    @DeleteMapping("/withdrawal")
    public Long withdrawal(@Valid @RequestBody WithdrawalMemberRequest request) {
        log.debug("WithdrawalMemberRequest={}", request);
        Long memberId = memberService.withdrawal(request.getLoginId(), request.getLoginPw());

        log.info("withdrawal member={}", memberId);
        return memberId;
    }
}
