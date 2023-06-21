package accio.hogsmeade.store.client.point;

import accio.hogsmeade.store.common.TimeBaseEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class PointHistory extends TimeBaseEntity {

    @Id @GeneratedValue
    @Column(name = "point_history_id")
    private Long id;
    private int amount;
    @Enumerated(STRING)
    @Column(nullable = false, updatable = false, length = 20)
    private PointHistoryType type;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "point_id")
    private Point point;

    @Builder
    public PointHistory(Long id, int amount, PointHistoryType type, Point point) {
        this.id = id;
        this.amount = amount;
        this.type = type;
        this.point = point;
    }
}
