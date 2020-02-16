package gp.training.kim.bar.ropository;

import gp.training.kim.bar.dbo.AuthInfoDBO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthInfoRepository extends JpaRepository<AuthInfoDBO, Long> {
    Optional<AuthInfoDBO> findByLogin(String username);
}
