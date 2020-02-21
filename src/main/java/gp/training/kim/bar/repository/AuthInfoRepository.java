package gp.training.kim.bar.repository;

import gp.training.kim.bar.dbo.AuthInfoDBO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthInfoRepository extends JpaRepository<AuthInfoDBO, Long> {
	Optional<AuthInfoDBO> findByLogin(String login);
}
