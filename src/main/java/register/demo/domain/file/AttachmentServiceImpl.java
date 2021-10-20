package register.demo.domain.file;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AttachmentServiceImpl implements AttachmentService{

    private final AttachmentRepository attachmentRepository;
    private final FileStore fileStore;

    public List<Attachment> saveImages(List<MultipartFile> multipartFiles) throws IOException {
        List<Attachment> attachments = fileStore.storeFiles(multipartFiles, AttachmentType.IMAGE);
        List<Attachment> attachedImages = attachmentRepository.saveAll(attachments);
        return attachedImages;
    }

    public List<Attachment> saveGeneralFiles(List<MultipartFile> multipartFiles) throws IOException {
        List<Attachment> attachments = fileStore.storeFiles(multipartFiles, AttachmentType.GENERAL);
        List<Attachment> attachedFiles = attachmentRepository.saveAll(attachments);
        return attachedFiles;
    }


    public Map<AttachmentType, List<Attachment>> findAttachments() {
        List<Attachment> attachments = attachmentRepository.findAll();
        Map<AttachmentType, List<Attachment>> result = attachments.stream()
                .collect(Collectors.groupingBy(Attachment::getAttachmentType));

        return result;
    }
}
