package gp.training.kim.bar.service;

import gp.training.kim.bar.controller.entity.Check;
import gp.training.kim.bar.controller.entity.Menu;
import gp.training.kim.bar.controller.entity.TableCheck;

import java.util.List;

public interface OfferServise {
    Menu getMenu();

    void makeOrder(Integer visitorId, List<Integer> offerIds);

    Check getCheck(Integer visitorId);

    TableCheck getTableCheck(Integer visitorId, String visitors);
}
