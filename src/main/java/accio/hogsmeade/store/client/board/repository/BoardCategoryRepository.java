package accio.hogsmeade.store.client.board.repository;

import accio.hogsmeade.store.client.board.BoardCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BoardCategoryRepository extends JpaRepository<BoardCategory, Long> {

    @Query("select bc.id from BoardCategory bc where bc.name=:name")
    Optional<Long> existName(@Param("name") String name);
}
