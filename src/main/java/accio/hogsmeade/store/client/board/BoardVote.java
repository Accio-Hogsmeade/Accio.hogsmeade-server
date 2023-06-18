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
public class BoardVote extends TimeBaseEntity {

    @Id @GeneratedValue
    @Column(name = "board_vote_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @Builder
    public BoardVote(Long id, Member member, Board board) {
        this.id = id;
        this.member = member;
        this.board = board;
    }
}
