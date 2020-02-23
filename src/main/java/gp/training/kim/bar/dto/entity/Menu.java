package gp.training.kim.bar.dto.entity;

import gp.training.kim.bar.dto.OfferDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@AllArgsConstructor
@ToString(includeFieldNames = true)
public class Menu {
	private List<OfferDTO> beer;

	private List<OfferDTO> food;
}
