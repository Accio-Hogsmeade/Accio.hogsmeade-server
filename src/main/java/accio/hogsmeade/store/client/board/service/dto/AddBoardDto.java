package accio.hogsmeade.store.client.board.service.dto;

import accio.hogsmeade.store.common.UploadFile;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class AddBoardDto {

    private Long boardCategoryId;
    private String title;
    private String content;
    private List<UploadFile> files;

    @Builder
    public AddBoardDto(Long boardCategoryId, String title, String content, List<UploadFile> files) {
        this.boardCategoryId = boardCategoryId;
        this.title = title;
        this.content = content;
        this.files = files;
    }
}
