package accio.hogsmeade.store.store.item.service.dto;

import accio.hogsmeade.store.common.UploadFile;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class AddItemDto {
    private String name;
    private String info;
    private int price;
    private int quantity;
    private List<UploadFile> files;

    @Builder
    public AddItemDto(String name, String info, int price, int quantity, List<UploadFile> files) {
        this.name = name;
        this.info = info;
        this.price = price;
        this.quantity = quantity;
        this.files = files;
    }
}
