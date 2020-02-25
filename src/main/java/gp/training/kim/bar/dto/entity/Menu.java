package gp.training.kim.bar.dto.entity;

import gp.training.kim.bar.dto.OfferDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@ToString(includeFieldNames = true)
public class Menu {
	private Map<String, List<OfferDTO>> menu;
}
