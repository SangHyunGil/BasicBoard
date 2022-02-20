package register.demo.category.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import register.demo.category.domain.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
