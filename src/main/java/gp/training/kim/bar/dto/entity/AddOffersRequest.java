package gp.training.kim.bar.dto.entity;

import lombok.Data;
import lombok.ToString;

import java.util.Map;

@Data
@ToString(includeFieldNames = true)
public class AddOffersRequest {
	private Map<Long, Integer> offers;
}
