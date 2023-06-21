package accio.hogsmeade.store.client.point.service.impl;

import accio.hogsmeade.store.client.point.repository.PointRepository;
import accio.hogsmeade.store.client.point.service.PointService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PointServiceImpl implements PointService {

    private final PointRepository pointRepository;

    @Override
    public Long addPoint(String loginId, int amount) {
        return null;
    }
}
