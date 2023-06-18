package accio.hogsmeade.store.client.board;

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
public class BoardReport extends TimeBaseEntity {

    @Id @GeneratedValue
    @Column(name = "board_report_id")
    private Long id;
    @Column(nullable = false, updatable = false, length = 100)
    private String reason;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @Builder
    public BoardReport(Long id, String reason, Member member, Board board) {
        this.id = id;
        this.reason = reason;
        this.member = member;
        this.board = board;
    }

    //== 연관관계 편의 메서드 ==//
    public static BoardReport create(String reason, Long memberId, Long boardId) {
        return BoardReport.builder()
                .reason(reason)
                .member(Member.builder().id(memberId).build())
                .board(Board.builder().id(boardId).build())
                .build();
    }
}
