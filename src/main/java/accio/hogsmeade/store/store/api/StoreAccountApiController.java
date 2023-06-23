package accio.hogsmeade.store.store.api;

import accio.hogsmeade.store.client.api.request.member.EditLoginPwRequest;
import accio.hogsmeade.store.client.member.service.dto.EditLoginPwDto;
import accio.hogsmeade.store.jwt.SecurityUtil;
import accio.hogsmeade.store.jwt.TokenInfo;
import accio.hogsmeade.store.store.api.request.store.LoginStoreRequest;
import accio.hogsmeade.store.store.api.request.store.SignupStoreRequest;
import accio.hogsmeade.store.store.api.request.store.WithdrawalStoreRequest;
import accio.hogsmeade.store.store.store.service.StoreAccountService;
import accio.hogsmeade.store.store.store.service.StoreService;
import accio.hogsmeade.store.store.store.service.dto.SignupStoreDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController()
@Slf4j
@RequiredArgsConstructor
@Api(tags = {"상점계정"})
@RequestMapping("/api/stores")
public class StoreAccountApiController {

    private final StoreService storeService;
    private final StoreAccountService storeAccountService;

    @ApiOperation(value = "상점 가입")
    @PostMapping("/signup")
    public Long signup(@RequestBody SignupStoreRequest request) {
        log.debug("SignupStoreRequest={}", request);
        SignupStoreDto dto = SignupStoreDto.builder()
                .loginId(request.getLoginId())
                .loginPw(request.getLoginPw())
                .shopkeeper(request.getShopkeeper())
                .tel(request.getTel())
                .email(request.getEmail())
                .storeName(request.getStoreName())
                .storeInfo(request.getStoreInfo())
                .build();

        Long storeId = storeService.signup(dto);
        log.info("signup store={}", storeId);
        return storeId;
    }

    @ApiOperation(value = "상점 탈퇴")
    @DeleteMapping("/withdrawal")
    public Long withdrawal(@Valid @RequestBody WithdrawalStoreRequest request, @PathVariable String id) {
        log.debug("WithdrawalMemberRequest={}", request);
        Long storeId = storeService.withdrawal(request.getLoginId(), request.getLoginPw());

        log.info("withdrawal store={}", storeId);
        return storeId;
    }

    @ApiOperation(value = "상점 로그인")
    @PostMapping("/login")
    public TokenInfo login(@Valid @RequestBody LoginStoreRequest request) {
        log.debug("LoginStoreRequest={}", request);
        TokenInfo tokenInfo = storeAccountService.login(request.getLoginId(), request.getLoginPw());

        log.debug("tokenInfo={}", tokenInfo);
        return tokenInfo;
    }

    @ApiOperation(value = "비밀번호 변경")
    @PutMapping("/loginPw")
    public void editLoginPw(@Valid @RequestBody EditLoginPwRequest request) {
        log.debug("EditLoginPwRequest={}", request);

        String loginId = SecurityUtil.getCurrentLoginId();
        log.debug("loginId={}", loginId);

        Long memberId = storeService.editLoginPw(loginId, request.getNowLoginPw(), request.getNewLoginPw());
        log.debug("editLoginPw={}", memberId);
    }
}
