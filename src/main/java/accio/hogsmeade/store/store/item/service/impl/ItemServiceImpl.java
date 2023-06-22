package accio.hogsmeade.store.store.item.service.impl;

import accio.hogsmeade.store.store.item.Item;
import accio.hogsmeade.store.store.item.repository.ItemRepository;
import accio.hogsmeade.store.store.item.service.ItemService;
import accio.hogsmeade.store.store.item.service.dto.AddItemDto;
import accio.hogsmeade.store.store.store.Store;
import accio.hogsmeade.store.store.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;
    private final StoreRepository storeRepository;

    @Override
    public Long addItem(String loginId, AddItemDto dto) {
        Store store = storeRepository.findByLoginId(loginId).orElseThrow(NoSuchElementException::new);

        Item item = Item.createItem(store, dto.getName(), dto.getInfo(), dto.getPrice(), dto.getQuantity(), dto.getFiles());
//        Item item = Item.createItem(store, dto);

        Item savedItem = itemRepository.save(item);
        return savedItem.getId();
    }
}
