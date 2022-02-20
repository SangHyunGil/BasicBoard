package register.demo.file.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import register.demo.file.domain.Attachment;

public interface AttachmentRepository extends JpaRepository<Attachment, Long> {
}
