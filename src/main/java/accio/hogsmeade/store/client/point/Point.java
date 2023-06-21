package accio.hogsmeade.store.client.point;

import accio.hogsmeade.store.client.member.Member;
import accio.hogsmeade.store.common.TimeBaseEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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

    @Builder
    public Point(Long id, int savedPoint, Member member) {
        this.id = id;
        this.savedPoint = savedPoint;
        this.member = member;
    }
}
