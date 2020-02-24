package gp.training.kim.bar.repository;

import gp.training.kim.bar.dbo.OfferDBO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OfferRepository extends JpaRepository<OfferDBO, Long> {
	List<OfferDBO> findByIdIn(Iterable<Long> ids);
}
