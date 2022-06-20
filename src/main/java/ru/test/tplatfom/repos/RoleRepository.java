package ru.test.tplatfom.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.test.tplatfom.domain.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
