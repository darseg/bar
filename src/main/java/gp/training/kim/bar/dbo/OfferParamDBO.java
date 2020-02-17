package gp.training.kim.bar.dbo;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Data
@Entity(name = "offer_param")
@NoArgsConstructor
public class OfferParamDBO extends AbstractBarEntity {

    @ManyToOne
    @JoinColumn(name = "offer_id")
    private OfferDBO offer;

    private String name;

    private String value;
}
