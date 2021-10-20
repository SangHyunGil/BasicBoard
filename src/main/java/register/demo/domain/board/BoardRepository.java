package register.demo.domain.board;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import register.demo.domain.student.Student;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {

    @Query("select b from Board b where b.title like concat('%', :title, '%')")
    List<Board> findByTitle(@Param("title") String title);

    List<Board> findByWriter(Student student);

}
