package register.demo.domain.board;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import register.demo.domain.file.Attachment;
import register.demo.domain.file.AttachmentService;
import register.demo.domain.student.Student;
import register.demo.domain.student.StudentService;
import register.demo.web.board.form.BoardAddForm;
import register.demo.web.board.form.BoardUpdateForm;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class BoardServiceImpl implements BoardService{

    private final BoardRepository boardRepository;
    private final AttachmentService attachmentService;
    public Board post(BoardAddForm boardForm, Student student) throws IOException {
        List<Attachment> attachments = new ArrayList<>();
        if (boardForm.getImageFiles() != null) {
            attachmentService.saveImages(boardForm.getImageFiles()).stream()
                    .forEach(a->attachments.add(a));
        }
        if (boardForm.getGeneralFiles() != null) {
            attachmentService.saveGeneralFiles(boardForm.getGeneralFiles())
                    .forEach(a->attachments.add(a));
        }

        Board board = boardForm.createBoard(student);
        System.out.println("board = " + board.getTitle());
        for (Attachment attachment : attachments) {
            log.info("attachments = {}", attachment);
        }

        attachments.stream()
                .forEach(attachment -> board.setAttachment(attachment));
        return boardRepository.save(board);
    }

    public Boolean update(Long boardId, BoardUpdateForm boardForm) {
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new IllegalArgumentException("해당 게시글은 존재하지 않습니다."));
        board.setTitle(boardForm.getTitle());
        board.setContent(boardForm.getContent());
        return true;
    }

    public Boolean delete(Long boardId) {
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new IllegalArgumentException("해당 게시글은 존재하지 않습니다."));
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
