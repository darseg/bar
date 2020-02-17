package gp.training.kim.bar.service;

import gp.training.kim.bar.dto.entity.Check;
import gp.training.kim.bar.dto.entity.Menu;
import gp.training.kim.bar.dto.entity.TableCheck;

import java.util.List;

public interface OfferService {
    Menu getMenu();

    void makeOrder(Integer visitorId, List<Integer> offerIds);
}
