package gp.training.kim.bar.repository;

import gp.training.kim.bar.dbo.IngredientDBO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IngredientRepository extends JpaRepository<IngredientDBO, Long> {
}
