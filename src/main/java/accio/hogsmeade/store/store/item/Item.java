package accio.hogsmeade.store.store.item;

import accio.hogsmeade.store.common.Active;
import accio.hogsmeade.store.common.TimeBaseEntity;
import accio.hogsmeade.store.common.UploadFile;
import accio.hogsmeade.store.store.store.Store;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

import static accio.hogsmeade.store.common.Active.ACTIVE;
import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Item extends TimeBaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Long id;
    @Column(nullable = false, length = 50)
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

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemImage> images = new ArrayList<>();

    @Builder
    public Item(Long id, String name, String info, int price, int quantity, Active active, Store store, List<ItemImage> images) {
        this.id = id;
        this.name = name;
        this.info = info;
        this.price = price;
        this.quantity = quantity;
        this.active = active;
        this.store = store;
        this.images = images;
    }

    /*
    연관관계 편의 매서드
     */
    public static Item createItem(Store store, String name, String info, int price, int quantity, List<UploadFile> files) {
        Item item = Item.builder()
                .name(name)
                .info(info)
                .price(price)
                .quantity(quantity)
                .active(ACTIVE)
                .store(store)
                .images(new ArrayList<>())
                .build();
        // TODO: 2023-06-22 이거 질문하기 dto
        for (UploadFile file : files) {
            ItemImage image = ItemImage.builder()
                    .uploadFile(file)
                    .item(item)
                    .build();
            item.addItemImage(image);
        }
        return item;
    }

    public void addItemImage(ItemImage image) {
        this.images.add(image);
    }

//    public static Item createItem(Store store, AddItemDto dto) {
//        Item item = Item.builder()
//                .name(dto.getName())
//                .info(dto.getInfo())
//                .price(dto.getPrice())
//                .quantity(dto.getQuantity())
//                .active(ACTIVE)
//                .store(store)
//                .images(new ArrayList<>())
//                .build();
//        for (UploadFile file : dto.getFiles()) {
//            ItemImage image = ItemImage.builder()
//                    .uploadFile(file)
//                    .item(item)
//                    .build();
//            item.addItemImage(image);
//        }
//        return item;
//    }
}
