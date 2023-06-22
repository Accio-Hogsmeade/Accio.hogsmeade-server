package accio.hogsmeade.store.store.api.request.item;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class RegistItemRequest {
    @NotBlank
    @Size(max = 50)
    private String name;
    @NotBlank
    private String info;
    private int price;
    private int quantity;
    private List<MultipartFile> files;
}
