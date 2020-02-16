package gp.training.kim.bar.repository;

import gp.training.kim.bar.dbo.UserDBO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserDBO, Long> {

}