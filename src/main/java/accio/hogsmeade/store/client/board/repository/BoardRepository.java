package accio.hogsmeade.store.client.board.repository;

import accio.hogsmeade.store.client.board.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {
}
