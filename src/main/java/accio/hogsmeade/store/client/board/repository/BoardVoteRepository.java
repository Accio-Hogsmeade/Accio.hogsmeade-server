package accio.hogsmeade.store.client.board.repository;

import accio.hogsmeade.store.client.board.BoardVote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardVoteRepository extends JpaRepository<BoardVote, Long> {
}
