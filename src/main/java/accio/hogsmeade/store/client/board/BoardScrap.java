package accio.hogsmeade.store.client.board;

import accio.hogsmeade.store.client.member.Member;
import accio.hogsmeade.store.common.TimeBaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardScrap extends TimeBaseEntity {

    @Id @GeneratedValue
    @Column(name = "board_scrap_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @Builder
    public BoardScrap(Long id, Member member, Board board) {
        this.id = id;
        this.member = member;
        this.board = board;
    }

    //== 연관관계 편의 메서드 ==//
    public static BoardScrap createBoardScrap(Member member, Board board) {
        BoardScrap boardScrap = BoardScrap.builder()
                .member(member)
                .board(board)
                .build();
        board.increaseScrapCount();
        return boardScrap;
    }
}
