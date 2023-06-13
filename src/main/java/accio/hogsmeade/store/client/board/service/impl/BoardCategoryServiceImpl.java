package accio.hogsmeade.store.client.board.service.impl;

import accio.hogsmeade.store.client.board.BoardCategory;
import accio.hogsmeade.store.client.board.repository.BoardCategoryRepository;
import accio.hogsmeade.store.client.board.service.BoardCategoryService;
import accio.hogsmeade.store.common.exception.DuplicateException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardCategoryServiceImpl implements BoardCategoryService {

    private final BoardCategoryRepository boardCategoryRepository;

    @Override
    public Long addBoardCategory(String name) {
        Optional<Long> findBoardCategoryId = boardCategoryRepository.existName(name);
        if (findBoardCategoryId.isPresent()) {
            throw new DuplicateException();
        }

        BoardCategory boardCategory = BoardCategory.builder()
                .name(name)
                .build();
        BoardCategory savedBoardCategory = boardCategoryRepository.save(boardCategory);
        return savedBoardCategory.getId();
    }
}
