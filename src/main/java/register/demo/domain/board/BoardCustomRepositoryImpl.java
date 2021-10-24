package register.demo.domain.board;


import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import register.demo.web.board.dto.HotPostDto;
import register.demo.web.board.search.SearchCondition;
import register.demo.web.board.search.SearchType;

import javax.persistence.EntityManager;

import java.time.LocalDate;
import java.util.List;
import java.util.function.Supplier;

import static register.demo.domain.board.QBoard.board;
import static register.demo.domain.like.QPostLike.postLike;

@Slf4j
public class BoardCustomRepositoryImpl implements BoardCustomRepository{

    public final JPAQueryFactory queryFactory;

    public BoardCustomRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public List<Board> search(SearchCondition condition) {
        return queryFactory
                .selectFrom(board)
                .where(isSearchable(condition.getType(), condition.getContent()))
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

    public List<HotPostDto> todayHotPost() {
        return queryFactory
                .select(Projections.constructor(HotPostDto.class, postLike.board, postLike.board.count()))
                .from(postLike)
                .groupBy(postLike.board)
                .having(postLike.board.writeTime.stringValue().contains(LocalDate.now().toString()))
                .orderBy(postLike.board.count().desc(), postLike.board.writeTime.asc())
                .limit(3)
                .fetch();
    }

    public Page<Board> findBoardByPaging(Pageable pageable) {
        log.info("pageable : {}", pageable.getOffset());
        QueryResults<Board> results = queryFactory
                .selectFrom(board)
                .orderBy(board.writeTime.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<Board> content = results.getResults();
        long num = results.getTotal();

        return new PageImpl<>(content, pageable, num);
    }
}
