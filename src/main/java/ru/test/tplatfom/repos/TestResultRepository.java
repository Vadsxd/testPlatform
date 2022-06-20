package ru.test.tplatfom.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.test.tplatfom.domain.Test;
import ru.test.tplatfom.domain.TestResult;
import ru.test.tplatfom.domain.User;

import java.util.List;

@Repository
public interface TestResultRepository extends JpaRepository<TestResult, Long> {
    List<TestResult> findAllByTest(Test test);

    void deleteAllByTest(Test test);

    TestResult findByUserAndTest(User user, Test test);
}
