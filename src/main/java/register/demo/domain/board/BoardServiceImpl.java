package register.demo.domain.board;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import register.demo.domain.comments.CommentRepository;
import register.demo.domain.file.Attachment;
import register.demo.domain.file.AttachmentService;
import register.demo.domain.student.Student;
import register.demo.web.board.dto.BoardPostDto;
import register.demo.web.board.dto.BoardUpdateDto;

import java.io.IOException;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class BoardServiceImpl implements BoardService{

    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;
    private final AttachmentService attachmentService;

    public Board post(BoardPostDto boardPostDto) throws IOException {
        List<Attachment> attachments = attachmentService.saveFiles(boardPostDto.getAttachmentFiles());
        for (Attachment attachment : attachments) {
            log.info(attachment.getOriginFilename());
        }
        Board board = boardPostDto.createBoard();
        attachments.stream()
                .forEach(attachment -> board.setAttachment(attachment));

        return boardRepository.save(board);
    }

    public Boolean update(BoardUpdateDto boardUpdateDto) {
        Board board = boardRepository.findById(boardUpdateDto.getBoardId()).orElseThrow(() -> new IllegalArgumentException("해당 게시글은 존재하지 않습니다."));
        board.setTitle(boardUpdateDto.getTitle());
        board.setContent(boardUpdateDto.getContent());
        return true;
    }

    public Boolean delete(Long boardId) {
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new IllegalArgumentException("해당 게시글은 존재하지 않습니다."));
        commentRepository.deleteByBoardId(boardId);
        boardRepository.delete(board);
        return true;
    }

    public Boolean updateHit(Long boardId) {
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new IllegalArgumentException("해당 게시글은 존재하지 않습니다."));
        board.setHit(board.getHit()+1);
        return true;
    }

    public Board findBoard(Long boardId) {
        return boardRepository.findById(boardId).orElseThrow(() -> new IllegalArgumentException("해당 게시글은 존재하지 않습니다."));
    }

    public List<Board> findBoard(String title) {
        return boardRepository.findByTitle(title);
    }

    public List<Board> findBoard(Student student) {
        return boardRepository.findByWriter(student);
    }

    public List<Board> findBoards(Sort sort) {
        return boardRepository.findAll(sort);
    }


}
