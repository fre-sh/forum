package com.fresh.forum.dao;

import com.fresh.forum.model.Question;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

public interface QuestionDAO extends JpaRepository<Question, Integer> {

    List<Question> findByUserId(int userId, Pageable pageable);

    List<Question> findByTitleContains(String title);

    @Query(value = "select * from question q where :userId = 0 or q.user_id = :userId " +
            " order by created_date desc limit :offset, :limit", nativeQuery = true
    )
    List<Question> selectLatestQuestions(@Param("userId") int userId, @Param("offset") int offset, @Param("limit") int limit);

    Question findByTitle(String title);

    Question findById(int id);

    int countByTitle(String title);

    void deleteAllByIdIn(Collection<Integer> id);

    void deleteAllByUserId(Integer user_id);
}
