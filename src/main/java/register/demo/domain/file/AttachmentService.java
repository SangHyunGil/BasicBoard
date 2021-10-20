package register.demo.domain.file;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface AttachmentService {

    List<Attachment> saveImages(List<MultipartFile> attachments) throws IOException;

    List<Attachment> saveGeneralFiles(List<MultipartFile> attachments) throws IOException;

    Map<AttachmentType, List<Attachment>> findAttachments();
}
