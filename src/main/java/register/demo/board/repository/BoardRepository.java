package register.demo.board.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import register.demo.board.domain.Board;

import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long>, BoardCustomRepository {

    @EntityGraph(attributePaths = {"writer", "attachedFiles"})
    Optional<Board> findById(Long BoardId);

}
