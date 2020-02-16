package gp.training.kim.bar.ropository;

import gp.training.kim.bar.dbo.IngredientDBO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredientRepository extends JpaRepository<IngredientDBO, Long> {
}
