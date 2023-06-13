package accio.hogsmeade.store.client.board;

import accio.hogsmeade.store.common.TimeBaseEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class BoardCategory extends TimeBaseEntity {

    @Id @GeneratedValue
    @Column(name = "board_category_id")
    private Long id;
    private String name;

    @Builder
    public BoardCategory(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
