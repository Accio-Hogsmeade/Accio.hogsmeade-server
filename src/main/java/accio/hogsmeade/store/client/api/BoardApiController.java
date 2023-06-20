package accio.hogsmeade.store.client.api;

import accio.hogsmeade.store.client.api.request.board.AddBoardCommentRequest;
import accio.hogsmeade.store.client.api.request.board.AddBoardReportRequest;
import accio.hogsmeade.store.client.api.request.board.AddBoardRequest;
import accio.hogsmeade.store.client.api.request.board.EditBoardRequest;
import accio.hogsmeade.store.client.board.service.BoardService;
import accio.hogsmeade.store.client.board.service.dto.AddBoardDto;
import accio.hogsmeade.store.client.board.service.dto.EditBoardDto;
import accio.hogsmeade.store.common.FileStore;
import accio.hogsmeade.store.common.UploadFile;
import accio.hogsmeade.store.jwt.SecurityUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

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

    @ApiOperation(value = "게시글 수정")
    @PutMapping("/{boardId}")
    public void editBoard(@PathVariable Long boardId, @Valid @RequestBody EditBoardRequest request) {
        log.debug("EditBoardRequest={}", request);

        EditBoardDto dto = EditBoardDto.builder()
                .categoryId(request.getCategoryId())
                .title(request.getTitle())
                .content(request.getContent())
                .build();
        Long editedBoardId = boardService.editBoard(boardId, dto);
        log.debug("editBoard={}", editedBoardId);
    }

    @ApiOperation(value = "게시글 삭제")
    @DeleteMapping("/{boardId}")
    public void removeBoard(@PathVariable Long boardId) {
        log.debug("boardId={}", boardId);

        Long removedBoardId = boardService.removeBoard(boardId);
        log.debug("removeBoard={}", removedBoardId);
    }

    @ApiOperation(value = "게시글 추천")
    @PostMapping("/{boardId}/vote")
    public void addVote(@PathVariable Long boardId) {
        log.debug("boardId={}", boardId);
        String loginId = SecurityUtil.getCurrentLoginId();
        log.debug("loginId={}", loginId);

        Long boardVoteId = boardService.addVote(loginId, boardId);
        log.debug("addVote={}", boardVoteId);
    }

    @ApiOperation(value = "게시글 추천 취소")
    @DeleteMapping("/{boardId}/vote")
    public void cancelVote(@PathVariable Long boardId) {
        log.debug("boardId={}", boardId);
        String loginId = SecurityUtil.getCurrentLoginId();
        log.debug("loginId={}", loginId);

        Long boardVoteId = boardService.cancelVote(loginId, boardId);
        log.debug("cancelVote={}", boardVoteId);
    }

    @ApiOperation(value = "게시글 스크랩")
    @PostMapping("/{boardId}/scrap")
    public void addScrap(@PathVariable Long boardId) {
        log.debug("boardId={}", boardId);
        String loginId = SecurityUtil.getCurrentLoginId();
        log.debug("loginId={}", loginId);

        Long boardScrapId = boardService.addScrap(loginId, boardId);
        log.debug("addScrap={}", boardScrapId);
    }

    @ApiOperation(value = "게시글 스크랩 취소")
    @DeleteMapping("/{boardId}/scrap")
    public void cancelScrap(@PathVariable Long boardId) {
        log.debug("boardId={}", boardId);
        String loginId = SecurityUtil.getCurrentLoginId();
        log.debug("loginId={}", loginId);

        Long boardScrapId = boardService.cancelScrap(loginId, boardId);
        log.debug("cancelScrap={}", boardScrapId);
    }

    @ApiOperation(value = "게시글 신고")
    @PostMapping("/{boardId}/report")
    public void report(@PathVariable Long boardId, @Valid @RequestBody AddBoardReportRequest request) {
        log.debug("boardId={}", boardId);
        String loginId = SecurityUtil.getCurrentLoginId();
        log.debug("loginId={}", loginId);

        Long boardReportId = boardService.addReport(loginId, boardId, request.getReason());
        log.debug("report={}", boardReportId);
    }

    @ApiOperation(value = "게시글 댓글 등록")
    @PostMapping("/{boardId}/comment")
    public void addComment(@PathVariable Long boardId, @Valid @RequestBody AddBoardCommentRequest request) {
        log.debug("boardId={}", boardId);
        String loginId = SecurityUtil.getCurrentLoginId();
        log.debug("loginId={}", loginId);

        Long boardCommentId = boardService.addComment(loginId, boardId, request.getCommentId(), request.getContent());
        log.debug("addComment={}", boardCommentId);
    }
}
