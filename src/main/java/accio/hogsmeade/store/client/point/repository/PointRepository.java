package accio.hogsmeade.store.client.point.repository;

import accio.hogsmeade.store.client.point.Point;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PointRepository extends JpaRepository<Point, Long> {
}
