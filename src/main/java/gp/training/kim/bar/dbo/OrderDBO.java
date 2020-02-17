package gp.training.kim.bar.dbo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.List;

@Data
@Entity(name = "order")
public class OrderDBO extends AbstractBarEntity {
    @OneToOne(optional = false)
    @JoinColumn(name = "table_id", nullable = false)
    private TableDBO table;

    @OneToOne
    @JoinColumn(name = "user_id")
    private UserDBO user;

    @ManyToMany
    @JoinTable(
            name = "order_offer",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "offer_id"))
    private List<OfferDBO> offers;

    private boolean paid;
}