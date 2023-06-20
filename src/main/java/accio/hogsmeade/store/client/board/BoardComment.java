package accio.hogsmeade.store.client.board;

import accio.hogsmeade.store.client.member.Member;
import accio.hogsmeade.store.common.Active;
import accio.hogsmeade.store.common.TimeBaseEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static accio.hogsmeade.store.common.Active.ACTIVE;
import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class BoardComment extends TimeBaseEntity {

    @Id @GeneratedValue
    @Column(name = "board_comment_id")
    private Long id;
    @Lob
    @Column(nullable = false)
    private String content;
    @Enumerated(STRING)
    @Column(nullable = false, length = 20)
    private Active active;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "board_id")
    private Board board;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "parent_id")
    private BoardComment parent;

    @Builder
    public BoardComment(Long id, String content, Active active, Member member, Board board, BoardComment parent) {
        this.id = id;
        this.content = content;
        this.active = active;
        this.member = member;
        this.board = board;
        this.parent = parent;
    }

    //== 연관관계 편의 메서드 ==//
    public static BoardComment create(Member member, Board board, Long commentId,String content) {
        BoardComment boardComment = BoardComment.builder()
                .content(content)
                .active(ACTIVE)
                .member(member)
                .board(board)
                .parent(BoardComment.builder().id(commentId).build())
                .build();
        board.increaseCommentCount();
        return boardComment;
    }
}
