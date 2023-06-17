package accio.hogsmeade.store.client.board.service.dto;

import accio.hogsmeade.store.common.UploadFile;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class EditBoardDto {

    private Long categoryId;
    private String title;
    private String content;
    private List<UploadFile> files;

    @Builder
    public EditBoardDto(Long categoryId, String title, String content, List<UploadFile> files) {
        this.categoryId = categoryId;
        this.title = title;
        this.content = content;
        this.files = files;
    }
}
