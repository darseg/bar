package gp.training.kim.bar.repository;

import gp.training.kim.bar.dbo.OfferDBO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OfferRepository extends JpaRepository<OfferDBO, Long> {
}
