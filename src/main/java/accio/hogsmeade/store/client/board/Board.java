package accio.hogsmeade.store.client.board;

import accio.hogsmeade.store.client.member.Member;
import accio.hogsmeade.store.common.Active;
import accio.hogsmeade.store.common.TimeBaseEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.*;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Board extends TimeBaseEntity {

    @Id @GeneratedValue
    @Column(name = "board_id")
    private Long id;
    @Column(nullable = false, length = 50)
    private String title;
    @Lob
    @Column(nullable = false)
    private String content;
    private int voteCount;
    private int scrapCount;
    private int commentCount;
    @Enumerated(STRING)
    @Column(nullable = false, length = 20)
    private Active active;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "board_category_id")
    private BoardCategory category;

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BoardImage> images = new ArrayList<>();

    //== 생성자 ==//
    @Builder
    public Board(Long id, String title, String content, int voteCount, int scrapCount, int commentCount, Active active, Member member, BoardCategory category, List<BoardImage> images) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.voteCount = voteCount;
        this.scrapCount = scrapCount;
        this.commentCount = commentCount;
        this.active = active;
        this.member = member;
        this.category = category;
        this.images = images;
    }
}
