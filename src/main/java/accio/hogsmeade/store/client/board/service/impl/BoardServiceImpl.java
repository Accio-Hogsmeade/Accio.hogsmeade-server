package accio.hogsmeade.store.client.board.service.impl;

import accio.hogsmeade.store.client.board.Board;
import accio.hogsmeade.store.client.board.BoardVote;
import accio.hogsmeade.store.client.board.repository.BoardRepository;
import accio.hogsmeade.store.client.board.repository.BoardVoteRepository;
import accio.hogsmeade.store.client.board.service.BoardService;
import accio.hogsmeade.store.client.board.service.dto.AddBoardDto;
import accio.hogsmeade.store.client.board.service.dto.EditBoardDto;
import accio.hogsmeade.store.client.member.Member;
import accio.hogsmeade.store.client.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;
    private final BoardVoteRepository boardVoteRepository;
    private final MemberRepository memberRepository;

    @Override
    public Long addBoard(String loginId, AddBoardDto dto) {
        Member member = memberRepository.findByLoginId(loginId)
                .orElseThrow(NoSuchElementException::new);

        Board board = Board.createBoard(dto.getTitle(), dto.getContent(), member, dto.getBoardCategoryId(), dto.getFiles());
        boardRepository.save(board);

        return board.getId();
    }

    @Override
    public Long editBoard(Long boardId, EditBoardDto dto) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(NoSuchElementException::new);

        board.edit(dto.getTitle(), dto.getContent(), dto.getCategoryId());
        return board.getId();
    }

    @Override
    public Long removeBoard(Long boardId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(NoSuchElementException::new);

        board.remove();
        return board.getId();
    }

    @Override
    public Long addVote(String loginId, Long boardId) {
        Member member = memberRepository.findByLoginId(loginId)
                .orElseThrow(NoSuchElementException::new);
        Board board = boardRepository.findById(boardId)
                .orElseThrow(NoSuchElementException::new);

        BoardVote boardVote = BoardVote.createBoardVote(member, board);

        BoardVote savedBoardVote = boardVoteRepository.save(boardVote);

        return savedBoardVote.getId();
    }

    @Override
    public Long cancelVote(String loginId, Long boardId) {
        return null;
    }
}
