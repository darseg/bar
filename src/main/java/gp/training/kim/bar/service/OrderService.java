package gp.training.kim.bar.service;

import gp.training.kim.bar.dto.entity.Check;
import gp.training.kim.bar.dto.entity.TableCheck;

public interface OrderService {
    Check getCheck(Long guestId);

    TableCheck getTableCheck(Long visitorId, String guests);
}
