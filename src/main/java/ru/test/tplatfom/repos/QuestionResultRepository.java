package ru.test.tplatfom.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.test.tplatfom.domain.Question;
import ru.test.tplatfom.domain.QuestionResult;
import ru.test.tplatfom.domain.User;

@Repository
public interface QuestionResultRepository extends JpaRepository<QuestionResult, Long> {
    void deleteAllByQuestion(Question question);

    QuestionResult findByQuestionAndUser(Question question, User user);
}
