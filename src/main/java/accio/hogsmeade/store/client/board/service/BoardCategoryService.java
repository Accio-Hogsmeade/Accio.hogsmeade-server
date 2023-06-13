package accio.hogsmeade.store.client.board.service;

import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface BoardCategoryService {

    Long addBoardCategory(String name);
}
