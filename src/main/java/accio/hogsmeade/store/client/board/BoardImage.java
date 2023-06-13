package accio.hogsmeade.store.client.board;

import accio.hogsmeade.store.common.TimeBaseEntity;
import accio.hogsmeade.store.common.UploadFile;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class BoardImage extends TimeBaseEntity {

    @Id @GeneratedValue
    @Column(name = "board_image_id")
    private Long id;
    @Embedded
    private UploadFile uploadFile;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    //== 생성자 ==//
    @Builder
    public BoardImage(Long id, UploadFile uploadFile, Board board) {
        this.id = id;
        this.uploadFile = uploadFile;
        this.board = board;
    }
}
