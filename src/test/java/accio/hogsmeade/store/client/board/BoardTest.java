package accio.hogsmeade.store.client.board;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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
}