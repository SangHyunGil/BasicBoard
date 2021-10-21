package register.demo.domain.file;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface AttachmentService {

    List<Attachment> saveFiles(Map<AttachmentType, List<MultipartFile>> multipartFileListMap) throws IOException;

    Map<AttachmentType, List<Attachment>> findAttachments();
}
