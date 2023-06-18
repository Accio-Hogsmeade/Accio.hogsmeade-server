package accio.hogsmeade.store.client.api.request.board;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class AddBoardReportRequest {

    @NotBlank
    @Size(max = 100)
    private String reason;
}
