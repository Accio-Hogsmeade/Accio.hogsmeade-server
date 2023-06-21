package accio.hogsmeade.store.store.item;

import accio.hogsmeade.store.common.Active;
import accio.hogsmeade.store.common.TimeBaseEntity;
import accio.hogsmeade.store.store.store.Store;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Item extends TimeBaseEntity {

    @Id @GeneratedValue
    @Column(name = "item_id")
    private Long id;
    @Column(nullable = false,length = 50)
    private String name;
    @Lob
    @Column(nullable = false)
    private String info;
    private int price;
    private int quantity;
    @Enumerated(STRING)
    @Column(nullable = false, length = 20)
    private Active active;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    @Builder
    public Item(Long id, String name, String info, int price, int quantity, Active active, Store store) {
        this.id = id;
        this.name = name;
        this.info = info;
        this.price = price;
        this.quantity = quantity;
        this.active = active;
        this.store = store;
    }
}
