package gp.training.kim.bar.service;

import gp.training.kim.bar.dto.entity.Check;
import gp.training.kim.bar.dto.entity.TableCheck;

import java.util.List;

public interface OrderService {
	Check getCheck(Long guestId);

	TableCheck getTableCheck(Long visitorId, String guests);

	void makeOrder(Integer visitorId, List<Integer> offerIds);
}
