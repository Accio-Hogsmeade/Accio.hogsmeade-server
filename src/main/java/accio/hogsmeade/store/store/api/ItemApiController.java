package accio.hogsmeade.store.store.api;

import accio.hogsmeade.store.common.FileStore;
import accio.hogsmeade.store.common.UploadFile;
import accio.hogsmeade.store.jwt.SecurityUtil;
import accio.hogsmeade.store.store.api.request.item.RegistItemRequest;
import accio.hogsmeade.store.store.item.service.ItemService;
import accio.hogsmeade.store.store.item.service.dto.AddItemDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.IOException;
import java.util.List;

@RestController()
@Slf4j
@RequiredArgsConstructor
@Api(tags = {"상점 - 물품 관리"})
@RequestMapping("/api/items")
public class ItemApiController {
    private final ItemService itemService;
    private final FileStore fileStore;

    @ApiOperation(value = "물품 등록")
    @PostMapping
    public Long registItem(@RequestBody RegistItemRequest request) throws IOException {
        log.debug("물품 등록 request = {}", request);
        String loginId = SecurityUtil.getCurrentLoginId();
        log.debug("로그인 아이디 = {}", loginId);

        List<UploadFile> files = fileStore.storeFiles(request.getFiles());

        AddItemDto dto = AddItemDto.builder()
                .name(request.getName())
                .info(request.getInfo())
                .price(request.getPrice())
                .quantity(request.getQuantity())
                .files(files)
                .build();

        Long itemId = itemService.addItem(loginId, dto);
        log.debug("아이템 등록됨 id = {}", itemId);

        return itemId;
    }
}
