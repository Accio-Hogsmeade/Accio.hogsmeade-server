package accio.hogsmeade.store.store.item;

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
public class ItemImage {
    @Id
    @GeneratedValue
    @Column(name = "item_image_id")
    private Long id;
    @Embedded
    private UploadFile uploadFile;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @Builder
    public ItemImage(Long id, UploadFile uploadFile, Item item) {
        this.id = id;
        this.uploadFile = uploadFile;
        this.item = item;
    }
}
