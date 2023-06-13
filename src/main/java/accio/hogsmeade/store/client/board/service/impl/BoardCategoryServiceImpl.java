package accio.hogsmeade.store.client.board.service.impl;

import accio.hogsmeade.store.client.board.repository.BoardCategoryRepository;
import accio.hogsmeade.store.client.board.service.BoardCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardCategoryServiceImpl implements BoardCategoryService {

    private final BoardCategoryRepository boardCategoryRepository;

    @Override
    public Long addBoardCategory(String name) {
        return null;
    }
}
