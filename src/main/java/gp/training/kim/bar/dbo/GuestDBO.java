package gp.training.kim.bar.dbo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity(name = "guest")
public class GuestDBO extends AbstractBarEntity {
    private List<OfferDBO> order;
}