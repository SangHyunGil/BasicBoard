package register.demo.domain.comments;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    public void reply(Long id, Comment comment) {
        commentRepository.saveComment(id, comment);
    }

    public List<Comment> findComments(Long id) {
        return commentRepository.findAllComment(id);
    }
}
