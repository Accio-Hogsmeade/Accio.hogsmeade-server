package accio.hogsmeade.store.client.api.request.board;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class EditBoardRequest {

    @NotNull
    private Long categoryId;
    @NotBlank
    @Size(max = 50)
    private String title;
    @NotBlank
    private String content;
}
