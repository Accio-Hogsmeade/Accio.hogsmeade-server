package accio.hogsmeade.store.client.point.service;

import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface PointService {

    Long addPoint(String loginId, int totalPrice);

    Long usePoint(String loginId, int amount);
}
