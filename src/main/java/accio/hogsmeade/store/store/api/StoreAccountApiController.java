package accio.hogsmeade.store.store.api;

import accio.hogsmeade.store.store.api.request.store.SignupStoreRequest;
import accio.hogsmeade.store.store.store.service.StoreService;
import accio.hogsmeade.store.store.store.service.dto.SignupStoreDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@Api(tags = {"상점계정"})
public class StoreAccountApiController {

    private final StoreService storeService;

    @ApiOperation(value = "상점 가입")
    @PostMapping("/store-signup")
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
}
