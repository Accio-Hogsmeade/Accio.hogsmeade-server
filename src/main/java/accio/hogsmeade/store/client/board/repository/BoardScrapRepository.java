package accio.hogsmeade.store.client.board.repository;

import accio.hogsmeade.store.client.board.BoardScrap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BoardScrapRepository extends JpaRepository<BoardScrap, Long> {

    @Query("select bs from BoardScrap bs " +
            "join fetch bs.member m " +
            "join fetch bs.board b " +
            "where m.loginId=:loginId and b.id=:boardId")
    Optional<BoardScrap> findByLoginId(@Param("loginId") String loginId, @Param("boardId") Long boardId);
}
