package register.demo.web.board.form;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import register.demo.domain.category.Category;
import register.demo.domain.category.CategoryType;
import register.demo.domain.file.AttachmentType;
import register.demo.domain.student.Student;
import register.demo.web.board.dto.BoardPostDto;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Data
@NoArgsConstructor
public class BoardAddForm {
    @NotBlank
    private String title;
    @NotBlank
    private String content;
    private CategoryType categoryType;
    private List<MultipartFile> imageFiles;
    private List<MultipartFile> generalFiles;

    @Builder
    public BoardAddForm(String title, String content, CategoryType categoryType, List<MultipartFile> imageFiles, List<MultipartFile> generalFiles) {
        this.title = title;
        this.content = content;
        this.categoryType = categoryType;
        this.imageFiles = (imageFiles != null) ? imageFiles : new ArrayList<>();
        this.generalFiles = (generalFiles != null) ? generalFiles : new ArrayList<>();
    }

    public BoardPostDto createBoardPostDto(Student student) {
        Map<AttachmentType, List<MultipartFile>> attachments = getAttachmentTypeListMap();
        return BoardPostDto.builder()
                .title(title)
                .writer(student)
                .content(content)
                .category(new Category(categoryType))
                .attachmentFiles(attachments)
                .build();
    }

    private Map<AttachmentType, List<MultipartFile>> getAttachmentTypeListMap() {
        Map<AttachmentType, List<MultipartFile>> attachments = new ConcurrentHashMap<>();
        attachments.put(AttachmentType.IMAGE, imageFiles);
        attachments.put(AttachmentType.GENERAL, generalFiles);
        return attachments;
    }
}
