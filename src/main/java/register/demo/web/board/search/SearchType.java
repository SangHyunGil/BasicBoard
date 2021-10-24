package register.demo.web.board.search;


import lombok.Getter;

@Getter
public enum SearchType {
    TIT("제목"), STUD("작성자"), TITCONT("제목+작성자");

    String description;

    SearchType(String description) {
        this.description = description;
    }
}
