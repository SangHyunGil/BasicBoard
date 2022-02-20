package register.demo.board.controller.search;


import lombok.Getter;

@Getter
public enum SearchType {
    TIT("제목"), STUD("작성자"), TITCONT("제목+내용");

    String description;

    SearchType(String description) {
        this.description = description;
    }
}
