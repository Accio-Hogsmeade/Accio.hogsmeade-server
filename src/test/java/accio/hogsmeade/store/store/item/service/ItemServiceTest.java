package accio.hogsmeade.store.store.item.service;

import accio.hogsmeade.store.common.UploadFile;
import accio.hogsmeade.store.store.item.Item;
import accio.hogsmeade.store.store.item.repository.ItemRepository;
import accio.hogsmeade.store.store.item.service.dto.AddItemDto;
import accio.hogsmeade.store.store.store.Store;
import accio.hogsmeade.store.store.store.repository.StoreRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Optional;

import static accio.hogsmeade.store.common.Active.ACTIVE;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class ItemServiceTest {

    @Autowired
    private ItemService itemService;
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private StoreRepository storeRepository;

    @Test
    @DisplayName("물품 등록")
    void addItem(){
        //given
        Store store = insertStore();
        UploadFile uploadFile = UploadFile.builder()
                .uploadFileName("upload_file_name.jpg")
                .storeFileName("store_file_name.jpg")
                .build();
        AddItemDto dto = AddItemDto.builder()
                .name("물품명")
                .info("상품설명")
                .price(1000)
                .quantity(20)
                .files(Collections.singletonList(uploadFile))
                .build();

        //when
        Long itemId = itemService.addItem(store.getLoginId(), dto);

        //then
        Optional<Item> findItem = itemRepository.findById(itemId);
        assertThat(findItem).isPresent();
    }

    private Store insertStore() {
        Store store = Store.builder()
                .loginId("storeid")
                .loginPw("storepw123!")
                .storeName("honeyduke")
                .storeInfo("butter beer")
                .email("honney@gmail.com")
                .tel("010-1234-1234")
                .shopkeeper("ron")
                .active(ACTIVE)
                .build();
        return storeRepository.save(store);
    }
}