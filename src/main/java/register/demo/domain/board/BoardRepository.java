package register.demo.domain.board;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import register.demo.domain.student.Student;

import java.util.List;
import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long>, BoardCustomRepository {

    @EntityGraph(attributePaths = {"writer", "attachedFiles"})
    Optional<Board> findById(Long BoardId);

}
