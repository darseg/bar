package gp.training.kim.bar.service;

import gp.training.kim.bar.dbo.OrderDBO;
import gp.training.kim.bar.dbo.TableDBO;
import gp.training.kim.bar.dbo.UserDBO;
import gp.training.kim.bar.dto.entity.BookingRequest;
import gp.training.kim.bar.dto.entity.Check;
import gp.training.kim.bar.dto.entity.TableCheck;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderService {
	Check getCheck(Long guestId);

	TableCheck getTableCheck(Long visitorId, String guests);

	OrderDBO createOrder(TableDBO table, UserDBO user, LocalDateTime from, LocalDateTime to);
}
