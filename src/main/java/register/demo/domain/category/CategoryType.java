package register.demo.domain.category;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public enum CategoryType {
    FRONT("Front Board"), BACK("Back Board");

    private String description;

    CategoryType(String description) {
        this.description = description;
    }
}
