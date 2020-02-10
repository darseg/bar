package gp.training.kim.bar.dbo;

import lombok.Data;

import java.util.List;

@Data
public class VisitorDBO {
    final Integer id;

    final List<OfferDBO> order;
}