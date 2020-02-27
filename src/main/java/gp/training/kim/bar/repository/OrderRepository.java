package gp.training.kim.bar.repository;

import gp.training.kim.bar.dbo.OrderDBO;
import gp.training.kim.bar.dbo.TableDBO;
import gp.training.kim.bar.dbo.UserDBO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<OrderDBO, Long> {
	Optional<OrderDBO> findByTableAndUserIsNullAndPaidFalse(TableDBO table);

	Optional<OrderDBO> findByUserAndPaidFalse(UserDBO user);

	Optional<OrderDBO> getOrderDBOById(Long orderId);

	boolean existsByUserEqualsAndPaidFalse(UserDBO user);

	boolean existsByTableAndEndAfterAndStartBefore(TableDBO table, LocalDateTime start, LocalDateTime end);
}
