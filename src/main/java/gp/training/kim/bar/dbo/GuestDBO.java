package gp.training.kim.bar.dbo;

import lombok.Data;

import javax.persistence.Entity;
import java.util.List;

@Data
@Entity(name = "guest")
public class GuestDBO extends AbstractBarEntity {
    private List<OfferDBO> order;
}