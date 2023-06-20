package accio.hogsmeade.store.client.board.service;

import accio.hogsmeade.store.client.board.*;
import accio.hogsmeade.store.client.board.repository.*;
import accio.hogsmeade.store.client.board.service.dto.AddBoardDto;
import accio.hogsmeade.store.client.board.service.dto.EditBoardDto;
import accio.hogsmeade.store.client.member.Member;
import accio.hogsmeade.store.client.member.repository.MemberRepository;
import accio.hogsmeade.store.common.Address;
import accio.hogsmeade.store.common.UploadFile;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Optional;

import static accio.hogsmeade.store.client.member.Identity.STUDENT;
import static accio.hogsmeade.store.client.member.SchoolGroup.GRYFFINDOR;
import static accio.hogsmeade.store.common.Active.ACTIVE;
import static accio.hogsmeade.store.common.Active.DEACTIVE;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class BoardServiceTest {

    @Autowired
    private BoardService boardService;
    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private BoardCategoryRepository boardCategoryRepository;
    @Autowired
    private BoardVoteRepository boardVoteRepository;
    @Autowired
    private BoardScrapRepository boardScrapRepository;
    @Autowired
    private BoardReportRepository boardReportRepository;
    @Autowired
    private BoardCommentRepository boardCommentRepository;

    @Test
    @DisplayName("게시글 등록")
    void addBoard() {
        //given
        Member member = insertMember();
        BoardCategory boardCategory = insertBoardCategory();
        UploadFile uploadFile = UploadFile.builder()
                .uploadFileName("upload_file_name.jpg")
                .storeFileName("store_file_name.jpg")
                .build();
        AddBoardDto dto = AddBoardDto.builder()
                .boardCategoryId(boardCategory.getId())
                .title("board title")
                .content("board content")
                .files(Collections.singletonList(uploadFile))
                .build();

        //when
        Long boardId = boardService.addBoard(member.getLoginId(), dto);

        //then
        Optional<Board> findBoard = boardRepository.findById(boardId);
        assertThat(findBoard).isPresent();
    }

    @Test
    @DisplayName("게시물 수정")
    void editBoard() {
        //given
        Board board = insertBoard();
        EditBoardDto dto = EditBoardDto.builder()
                .title("new board title")
                .content("new board content")
                .categoryId(board.getCategory().getId())
                .build();

        //when
        Long boardId = boardService.editBoard(board.getId(), dto);

        //then
        Optional<Board> findBoard = boardRepository.findById(boardId);
        assertThat(findBoard).isPresent();
        assertThat(findBoard.get().getTitle()).isEqualTo(dto.getTitle());
    }

    @Test
    @DisplayName("게시글 삭제")
    void removeBoard() {
        //given
        Board board = insertBoard();

        //when
        Long boardId = boardService.removeBoard(board.getId());

        //then
        Optional<Board> findBoard = boardRepository.findById(boardId);
        assertThat(findBoard).isPresent();
        assertThat(findBoard.get().getActive()).isEqualTo(DEACTIVE);
    }
    
    @Test
    @DisplayName("게시글 추천")
    void addVote() {
        //given
        Board board = insertBoard();

        //when
        Long boardVoteId = boardService.addVote(board.getMember().getLoginId(), board.getId());

        //then
        Optional<BoardVote> findBoardVote = boardVoteRepository.findById(boardVoteId);
        assertThat(findBoardVote).isPresent();

        Optional<Board> findBoard = boardRepository.findById(board.getId());
        assertThat(findBoard).isPresent();
        assertThat(findBoard.get().getVoteCount()).isEqualTo(1);
    }

    @Test
    @DisplayName("게시글 추천 취소")
    void cancelVote() {
        //given
        Board board = insertBoard();
        BoardVote boardVote = BoardVote.createBoardVote(board.getMember(), board);
        BoardVote savedBoardVote = boardVoteRepository.save(boardVote);

        //when
        Long boardVoteId = boardService.cancelVote(savedBoardVote.getMember().getLoginId(), savedBoardVote.getBoard().getId());

        //then
        Optional<BoardVote> findBoardVote = boardVoteRepository.findById(boardVoteId);
        assertThat(findBoardVote).isEmpty();

        Optional<Board> findBoard = boardRepository.findById(board.getId());
        assertThat(findBoard).isPresent();
        assertThat(findBoard.get().getVoteCount()).isEqualTo(0);
    }

    @Test
    @DisplayName("게시글 스크랩")
    void addScrap() {
        //given
        Board board = insertBoard();

        //when
        Long boardScrapId = boardService.addScrap(board.getMember().getLoginId(), board.getId());

        //then
        Optional<BoardScrap> findBoardScrap = boardScrapRepository.findById(boardScrapId);
        assertThat(findBoardScrap).isPresent();

        Optional<Board> findBoard = boardRepository.findById(board.getId());
        assertThat(findBoard).isPresent();
        assertThat(findBoard.get().getScrapCount()).isEqualTo(1);
    }
    
    @Test
    @DisplayName("게시글 스크랩 취소")
    void cancelScrap() {
        //given
        Board board = insertBoard();
        BoardScrap boardScrap = BoardScrap.createBoardScrap(board.getMember(), board);
        BoardScrap savedBoardScrap = boardScrapRepository.save(boardScrap);

        //when
        Long boardScrapId = boardService.cancelScrap(savedBoardScrap.getMember().getLoginId(), savedBoardScrap.getBoard().getId());

        //then
        Optional<BoardScrap> findBoardScrap = boardScrapRepository.findById(boardScrapId);
        assertThat(findBoardScrap).isEmpty();

        Optional<Board> findBoard = boardRepository.findById(board.getId());
        assertThat(findBoard).isPresent();
        assertThat(findBoard.get().getScrapCount()).isEqualTo(0);
    }

    @Test
    @DisplayName("게시글 신고")
    void addReport() {
        //given
        Board board = insertBoard();

        //when
        Long boardReportId = boardService.addReport(board.getMember().getLoginId(), board.getId(), "report");

        //then
        Optional<BoardReport> findBoardReport = boardReportRepository.findById(boardReportId);
        assertThat(findBoardReport).isPresent();
    }

    @Test
    @DisplayName("게시글 댓글 등록")
    void addComment() {
        //given
        Member member = insertMember();
        Board board = insertBoard();
        String content = "board comment";

        //when
        Long boardCommentId = boardService.addComment(member.getLoginId(), board.getId(), null, content);

        //then
        Optional<BoardComment> findBoardComment = boardCommentRepository.findById(boardCommentId);
        assertThat(findBoardComment).isPresent();
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

    private BoardCategory insertBoardCategory() {
        BoardCategory boardCategory = BoardCategory.builder()
                .name("그리핀도르")
                .build();
        return boardCategoryRepository.save(boardCategory);
    }

    private Board insertBoard() {
        UploadFile uploadFile = UploadFile.builder()
                .uploadFileName("upload_file_name.jpg")
                .storeFileName("store_file_name.jpg")
                .build();
        Board board = Board.createBoard("board title", "board content", insertMember(), insertBoardCategory().getId(), Collections.singletonList(uploadFile));
        return boardRepository.save(board);
    }
}