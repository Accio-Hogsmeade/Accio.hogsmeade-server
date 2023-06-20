package accio.hogsmeade.store.client.board.repository;

import accio.hogsmeade.store.client.board.BoardComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardCommentRepository extends JpaRepository<BoardComment, Long> {
}
