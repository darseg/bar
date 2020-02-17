package gp.training.kim.bar.repository;

import gp.training.kim.bar.dbo.OrderDBO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<OrderDBO, Long> {
    List<OrderDBO> findByTableAndPaid(Long tableId, Boolean paid);

    OrderDBO findByUserAndPaid(Long userId, Boolean paid);
}
