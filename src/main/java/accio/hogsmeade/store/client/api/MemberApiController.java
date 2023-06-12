package accio.hogsmeade.store.client.api;

import accio.hogsmeade.store.client.api.request.member.EditEmailRequest;
import accio.hogsmeade.store.client.api.request.member.EditLoginPwRequest;
import accio.hogsmeade.store.client.api.request.member.EditTelRequest;
import accio.hogsmeade.store.client.member.service.MemberService;
import accio.hogsmeade.store.client.member.service.dto.EditLoginPwDto;
import accio.hogsmeade.store.jwt.SecurityUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@Slf4j
@RequiredArgsConstructor
@Api(tags = {"회원계정"})
@RequestMapping("/api/my")
public class MemberApiController {

    private final MemberService memberService;

    @ApiOperation(value = "비밀번호 변경")
    @PutMapping("/loginPw")
    public void editLoginPw(@Valid @RequestBody EditLoginPwRequest request) {
        log.debug("EditLoginPwRequest={}", request);

        String loginId = SecurityUtil.getCurrentLoginId();
        log.debug("loginId={}", loginId);

        EditLoginPwDto dto = EditLoginPwDto.builder()
                .nowLoginPw(request.getNowLoginPw())
                .newLoginPw(request.getNewLoginPw())
                .build();
        Long memberId = memberService.editLoginPw(loginId, dto);
        log.debug("editLoginPw={}", memberId);
    }

    @ApiOperation(value = "연락처 변경")
    @PutMapping("/tel")
    public void editTel(@Valid @RequestBody EditTelRequest request) {
        log.debug("EditTelRequest={}", request);

        String loginId = SecurityUtil.getCurrentLoginId();
        log.debug("loginId={}", loginId);

        Long memberId = memberService.editTel(loginId, request.getNewTel());
        log.debug("editTel={}", memberId);
    }

    @ApiOperation(value = "이메일 변경")
    @PutMapping("/email")
    public void editEmail(@Valid @RequestBody EditEmailRequest request) {
        log.debug("EditEmailRequest={}", request);

        String loginId = SecurityUtil.getCurrentLoginId();
        log.debug("loginId={}", loginId);

        Long memberId = memberService.editEmail(loginId, request.getNewEmail());
        log.debug("editEmail={}", memberId);
    }
}
