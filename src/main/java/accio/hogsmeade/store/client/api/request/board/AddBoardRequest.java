package accio.hogsmeade.store.client.api.request.board;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class AddBoardRequest {

    @NotNull
    private Long categoryId;
    @NotBlank
    @Size(max = 50)
    private String title;
    @NotBlank
    private String content;
    private List<MultipartFile> files;
}
