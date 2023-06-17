package accio.hogsmeade.store.client.api;

import accio.hogsmeade.store.client.api.request.board.AddBoardRequest;
import accio.hogsmeade.store.client.board.service.BoardService;
import accio.hogsmeade.store.client.board.service.dto.AddBoardDto;
import accio.hogsmeade.store.common.FileStore;
import accio.hogsmeade.store.common.UploadFile;
import accio.hogsmeade.store.jwt.SecurityUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@Api(tags = {"회원계정"})
@RequestMapping("/api/boards")
public class BoardApiController {

    private final BoardService boardService;
    private final FileStore fileStore;

    @ApiOperation(value = "게시글 등록")
    @PostMapping
    public void addBoard(@Valid @RequestBody AddBoardRequest request) throws IOException {
        log.debug("AddBoardRequest={}", request);
        String loginId = SecurityUtil.getCurrentLoginId();
        log.debug("loginId={}", loginId);

        List<UploadFile> files = fileStore.storeFiles(request.getFiles());

        AddBoardDto dto = AddBoardDto.builder()
                .boardCategoryId(request.getCategoryId())
                .title(request.getTitle())
                .content(request.getContent())
                .files(files)
                .build();
        Long boardId = boardService.addBoard(loginId, dto);
        log.debug("addBoard={}", boardId);
    }
}
