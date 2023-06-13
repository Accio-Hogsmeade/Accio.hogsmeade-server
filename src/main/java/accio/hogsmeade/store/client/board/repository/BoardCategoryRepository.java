package accio.hogsmeade.store.client.board.repository;

import accio.hogsmeade.store.client.board.BoardCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardCategoryRepository extends JpaRepository<BoardCategory, Long> {
}
