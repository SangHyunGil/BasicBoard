package register.demo.domain.board;


import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;

import java.util.List;
import java.util.function.Supplier;

import static register.demo.domain.board.QBoard.board;

public class BoardCustomRepositoryImpl implements BoardCustomRepository{

    public final JPAQueryFactory queryFactory;

    public BoardCustomRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<Board> search(SearchCondition condition) {
        return queryFactory
                .selectFrom(board)
                .where(isSearchable(condition.type, condition.content))
                .orderBy(board.writeTime.desc())
                .fetch();
    }

    BooleanBuilder isSearchable(SearchType sType, String content) {
        if (sType == SearchType.TIT) {
            return titleCt(content);
        }
        else if(sType == SearchType.STUD) {
            return userEq(content);
        }
        else {
            return titleCt(content).or(contentCt(content));
        }

    }
    BooleanBuilder userEq(String content) {
        return nullSafeBuilder(() -> board.writer.nickname.eq(content));
    }
    BooleanBuilder titleCt(String content) {
        return nullSafeBuilder(() -> board.title.contains(content));
    }

    BooleanBuilder contentCt(String content) {
        return nullSafeBuilder(() -> board.content.contains(content));
    }

    BooleanBuilder nullSafeBuilder(Supplier<BooleanExpression> f) {
        try {
            return new BooleanBuilder(f.get());
        } catch (Exception e) {
            return new BooleanBuilder();
        }
    }
}
