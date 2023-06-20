package accio.hogsmeade.store.client.board;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static accio.hogsmeade.store.common.Active.ACTIVE;
import static accio.hogsmeade.store.common.Active.DEACTIVE;
import static org.assertj.core.api.Assertions.assertThat;

class BoardTest {

    @Test
    @DisplayName("게시글 수정")
    void edit() {
        //given
        Board board = Board.builder()
                .title("board title")
                .content("board content")
                .category(BoardCategory.builder().id(1L).build())
                .build();
        String newTitle = "new board title";
        String newContent = "new board content";
        Long newCategoryId = 2L;

        //when
        board.edit(newTitle, newContent, newCategoryId);

        //then
        assertThat(board.getTitle()).isEqualTo(newTitle);
        assertThat(board.getContent()).isEqualTo(newContent);
        assertThat(board.getCategory().getId()).isEqualTo(newCategoryId);
    }

    @Test
    @DisplayName("게시글 삭제")
    void remove() {
        //given
        Board board = Board.builder()
                .active(ACTIVE)
                .build();

        //when
        board.remove();

        //then
        assertThat(board.getActive()).isEqualTo(DEACTIVE);
    }

    @Test
    @DisplayName("추천수 증가")
    void increaseVoteCount() {
        //given
        Board board = Board.builder()
                .voteCount(0)
                .build();

        //when
        board.increaseVoteCount();

        //then
        assertThat(board.getVoteCount()).isEqualTo(1);
    }

    @Test
    @DisplayName("추천수 감소")
    void decreaseVoteCount() {
        //given
        Board board = Board.builder()
                .voteCount(1)
                .build();

        //when
        board.decreaseVoteCount();

        //then
        assertThat(board.getVoteCount()).isEqualTo(0);
    }

    @Test
    @DisplayName("스크랩수 증가")
    void increaseScrapCount() {
        //given
        Board board = Board.builder()
                .scrapCount(0)
                .build();

        //when
        board.increaseScrapCount();

        //then
        assertThat(board.getScrapCount()).isEqualTo(1);
    }

    @Test
    @DisplayName("스크랩수 감소")
    void decreaseScrapCount() {
        //given
        Board board = Board.builder()
                .scrapCount(1)
                .build();

        //when
        board.decreaseScrapCount();

        //then
        assertThat(board.getScrapCount()).isEqualTo(0);
    }

    @Test
    @DisplayName("댓글수 증가")
    void increaseCommentCount() {
        //given
        Board board = Board.builder()
                .commentCount(0)
                .build();

        //when
        board.increaseCommentCount();

        //then
        assertThat(board.getCommentCount()).isEqualTo(1);
    }

    @Test
    @DisplayName("댓글수 감소")
    void decreaseCommentCount() {
        //given
        Board board = Board.builder()
                .commentCount(1)
                .build();

        //when
        board.decreaseCommentCount();

        //then
        assertThat(board.getCommentCount()).isEqualTo(0);
    }
}