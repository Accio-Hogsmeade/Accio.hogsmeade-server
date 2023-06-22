package accio.hogsmeade.store.client.point.repository;

import accio.hogsmeade.store.client.point.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PointRepository extends JpaRepository<Point, Long> {

    @Query("select p from Point p join fetch p.member m where m.loginId=:loginId")
    Optional<Point> findByLoginId(@Param("loginId") String loginId);
}
