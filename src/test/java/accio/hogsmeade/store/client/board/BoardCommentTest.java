package accio.hogsmeade.store.client.board;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static accio.hogsmeade.store.common.Active.ACTIVE;
import static accio.hogsmeade.store.common.Active.DEACTIVE;
import static org.assertj.core.api.Assertions.assertThat;

class BoardCommentTest {

    @Test
    @DisplayName("댓글 삭제")
    void remove() {
        //given
        BoardComment boardComment = BoardComment.builder()
                .active(ACTIVE)
                .build();

        //when
        boardComment.remove();

        //then
        assertThat(boardComment.getActive()).isEqualTo(DEACTIVE);
    }
}