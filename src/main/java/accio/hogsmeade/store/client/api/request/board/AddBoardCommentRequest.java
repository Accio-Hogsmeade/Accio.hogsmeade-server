package accio.hogsmeade.store.client.api.request.board;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class AddBoardCommentRequest {

    private Long commentId;
    @NotBlank
    private String content;
}
