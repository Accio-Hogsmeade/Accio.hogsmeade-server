package accio.hogsmeade.store.client.point.service.impl;

import accio.hogsmeade.store.client.point.service.PointPolicy;
import org.springframework.stereotype.Component;

@Component
public class RatePointPolicy implements PointPolicy {

    private final int savePercent = 1;

    @Override
    public int savePoint(int price) {
        return price * savePercent / 100;
    }
}
