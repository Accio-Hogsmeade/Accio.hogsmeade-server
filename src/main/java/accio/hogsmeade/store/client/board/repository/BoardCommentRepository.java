package accio.hogsmeade.store.client.board.repository;

import accio.hogsmeade.store.client.board.BoardComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BoardCommentRepository extends JpaRepository<BoardComment, Long> {

    @Query("select bc from BoardComment bc join fetch bc.board where bc.id=:id")
    Optional<BoardComment> findByIdWithBoard(@Param("id") Long id);
}
