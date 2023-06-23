package accio.hogsmeade.store.client.point;

import accio.hogsmeade.store.client.member.Member;
import accio.hogsmeade.store.common.TimeBaseEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

import static accio.hogsmeade.store.client.point.PointHistoryType.USE;
import static javax.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Point extends TimeBaseEntity {

    @Id @GeneratedValue
    @Column(name = "point_id")
    private Long id;
    private int savedPoint;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "point", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PointHistory> histories = new ArrayList<>();

    @Builder
    public Point(Long id, int savedPoint, Member member, List<PointHistory> histories) {
        this.id = id;
        this.savedPoint = savedPoint;
        this.member = member;
        this.histories = histories;
    }

    //== 연관관계 편의 메서드 ==//
    public void save(PointHistoryType type , int amount) {
        PointHistory pointHistory = PointHistory.builder()
                .type(type)
                .amount(amount)
                .point(this)
                .build();
        this.histories.add(pointHistory);
        this.savedPoint += amount;
    }

    public void use(int amount) {
        int result = this.savedPoint - amount;
        if (result < 0) {
            throw new IllegalStateException();
        }
        PointHistory pointHistory = PointHistory.builder()
                .type(USE)
                .amount(amount)
                .point(this)
                .build();
        this.histories.add(pointHistory);
        this.savedPoint = result;
    }
}
