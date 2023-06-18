package accio.hogsmeade.store.client.board.service;

import accio.hogsmeade.store.client.board.service.dto.AddBoardDto;
import accio.hogsmeade.store.client.board.service.dto.EditBoardDto;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface BoardService {

    Long addBoard(String loginId, AddBoardDto dto);

    Long editBoard(Long boardId, EditBoardDto dto);

    Long removeBoard(Long boardId);

    Long addVote(String loginId, Long boardId);

    Long cancelVote(String loginId, Long boardId);

    Long addScrap(String loginId, Long boardId);

    Long cancelScrap(String loginId, Long boardId);
}
