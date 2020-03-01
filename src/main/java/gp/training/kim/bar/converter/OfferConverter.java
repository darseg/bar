package gp.training.kim.bar.converter;

import gp.training.kim.bar.constant.OfferType;
import gp.training.kim.bar.dbo.OfferDBO;
import gp.training.kim.bar.dbo.OfferImageDBO;
import gp.training.kim.bar.dbo.OfferParamDBO;
import gp.training.kim.bar.dto.OfferDTO;
import gp.training.kim.bar.utils.BarUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class OfferConverter extends AbstractConverter<OfferDBO, OfferDTO> {
	public OfferConverter() {
		super(new String[]{"type", "params", "images", "recipeRows", "ingredients"});
	}

	@Override
	public OfferDTO convertToDto(final OfferDBO offerDBO) {
		final OfferDTO offerDTO = super.convertToDto(offerDBO);

		offerDTO.setType(offerDBO.getType().getName());
		final Map<String, String> params = new HashMap<>();
		offerDBO.getParams().forEach(offerParam -> params.put(offerParam.getName(), offerParam.getValue()));
		offerDTO.setParams(params);
		offerDTO.setImages(BarUtils.imagesFromDboToDto(offerDBO.getImages()));

		return offerDTO;
	}

	@Override
	public OfferDBO convertToDbo(final OfferDTO offerDTO) {
		final OfferDBO offerDBO = super.convertToDbo(offerDTO);

		offerDBO.setType(OfferType.valueOf(offerDTO.getType().toUpperCase()));
		offerDBO.setParams(offerDTO.getParams().entrySet()
				.stream().map(paramsEntry -> new OfferParamDBO(offerDBO, paramsEntry.getKey(), paramsEntry.getValue()))
				.collect(Collectors.toList()));
		offerDBO.setImages(offerDTO.getImages()
				.stream().map(imageUrl -> new OfferImageDBO(offerDBO, imageUrl)).collect(Collectors.toList()));

		// It would be nice to implement the conversion of ingredients, but for this I would have to use a repository

		return offerDBO;
	}

	@Override
	protected OfferDTO constructDto() {
		return new OfferDTO();
	}

	@Override
	protected OfferDBO constructDbo() {
		return new OfferDBO();
	}
}
