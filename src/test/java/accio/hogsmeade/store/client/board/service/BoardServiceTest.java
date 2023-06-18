package accio.hogsmeade.store.client.board.service;

import accio.hogsmeade.store.client.board.Board;
import accio.hogsmeade.store.client.board.BoardCategory;
import accio.hogsmeade.store.client.board.repository.BoardCategoryRepository;
import accio.hogsmeade.store.client.board.repository.BoardRepository;
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