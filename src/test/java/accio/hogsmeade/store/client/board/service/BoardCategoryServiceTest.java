package accio.hogsmeade.store.client.board.service;

import accio.hogsmeade.store.client.board.BoardCategory;
import accio.hogsmeade.store.client.board.repository.BoardCategoryRepository;
import accio.hogsmeade.store.common.exception.DuplicateException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
class BoardCategoryServiceTest {

    @Autowired
    private BoardCategoryService boardCategoryService;
    @Autowired
    private BoardCategoryRepository boardCategoryRepository; 

    @Test
    @DisplayName("게시물 카테고리 등록#카테고리 이름 중복")
    void duplicationName() {
        //given
        BoardCategory boardCategory = insertBoardCategory();

        //when

        //then
        assertThatThrownBy(() -> boardCategoryService.addBoardCategory(boardCategory.getName()))
                .isInstanceOf(DuplicateException.class);
    }

    @Test
    @DisplayName("게시물 카테고리 등록")
    void addBoardCategory() {
        //given
        String boardCategoryName = "그리핀도르";

        //when
        Long boardCategoryId = boardCategoryService.addBoardCategory(boardCategoryName);

        //then
        Optional<BoardCategory> findBoardCategory = boardCategoryRepository.findById(boardCategoryId);
        assertThat(findBoardCategory).isPresent();
    }

    private BoardCategory insertBoardCategory() {
        BoardCategory boardCategory = BoardCategory.builder()
                .name("그리핀도르")
                .build();
        return boardCategoryRepository.save(boardCategory);
    }
}