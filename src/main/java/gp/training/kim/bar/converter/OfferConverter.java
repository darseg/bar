package gp.training.kim.bar.converter;

import gp.training.kim.bar.dbo.OfferDBO;
import gp.training.kim.bar.dto.OfferDTO;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class OfferConverter extends AbstractConverter<OfferDBO, OfferDTO> {

	@Override
	public OfferDTO convertToDto(final OfferDBO offerDBO) {
		final Map<String, String> params = new HashMap<>();
		offerDBO.getParams().forEach(offerParam -> params.put(offerParam.getName(), offerParam.getValue()));

		return new OfferDTO(offerDBO.getId(), offerDBO.getName(), offerDBO.getDescription(), params, offerDBO.getPrice());
	}

	@Override
	public OfferDBO convertToDbo(OfferDTO offerDTO) {
		return null;
	}

	@Override
	protected OfferDTO constructDto() {
		return null;
	}

	@Override
	protected OfferDBO constructDbo() {
		return null;
	}
}
