package accio.hogsmeade.store.client.board.repository;

import accio.hogsmeade.store.client.board.BoardVote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BoardVoteRepository extends JpaRepository<BoardVote, Long> {

    @Query("select bv from BoardVote bv " +
            "join fetch bv.member m " +
            "join fetch bv.board b " +
            "where m.loginId=:loginId and b.id=:boardId")
    Optional<BoardVote> findByLoginId(@Param("loginId") String loginId, @Param("boardId") Long boardId);
}
