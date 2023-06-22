package accio.hogsmeade.store.store.item.service;

import accio.hogsmeade.store.store.item.service.dto.AddItemDto;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface ItemService {

    Long addItem(String loginId, AddItemDto dto);
}
