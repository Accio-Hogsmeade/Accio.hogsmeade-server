package accio.hogsmeade.store.client.point.service.impl;

import accio.hogsmeade.store.client.point.Point;
import accio.hogsmeade.store.client.point.repository.PointRepository;
import accio.hogsmeade.store.client.point.service.PointPolicy;
import accio.hogsmeade.store.client.point.service.PointService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

import static accio.hogsmeade.store.client.point.PointHistoryType.SAVE;

@Service
@RequiredArgsConstructor
public class PointServiceImpl implements PointService {

    private final PointRepository pointRepository;
    private final PointPolicy pointPolicy;

    @Override
    public Long addPoint(String loginId, int totalPrice) {
        Point point = pointRepository.findByLoginId(loginId)
                .orElseThrow(NoSuchElementException::new);

        int amount = pointPolicy.savePoint(totalPrice);
        point.addPointHistory(SAVE, amount);

        return point.getId();
    }
}
