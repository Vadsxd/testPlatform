package ru.test.tplatfom.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.test.tplatfom.domain.Question;
import ru.test.tplatfom.domain.Test;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<Question> findAllByTest(Test test);

    void deleteAllByTest(Test test);
}
