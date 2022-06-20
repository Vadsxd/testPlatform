package ru.test.tplatfom.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.test.tplatfom.domain.Test;

@Repository
public interface TestRepository extends JpaRepository<Test, Long> {
}
