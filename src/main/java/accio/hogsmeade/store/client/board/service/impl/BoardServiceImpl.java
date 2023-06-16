package accio.hogsmeade.store.client.board.service.impl;

import accio.hogsmeade.store.client.board.repository.BoardRepository;
import accio.hogsmeade.store.client.board.service.BoardService;
import accio.hogsmeade.store.client.board.service.dto.AddBoardDto;
import accio.hogsmeade.store.client.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;

    @Override
    public Long addBoard(String loginId, AddBoardDto dto) {
        return null;
    }
}
