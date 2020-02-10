package gp.training.kim.bar.converter;

import gp.training.kim.bar.dbo.OfferDBO;
import gp.training.kim.bar.dto.OfferDTO;
import org.springframework.stereotype.Service;

@Service
public class OfferConverter extends AbstractConverter<OfferDBO, OfferDTO> {

    @Override
    public OfferDTO convertToDto(final OfferDBO offerDBO) {
        return new OfferDTO(offerDBO.getId(), offerDBO.getName(), offerDBO.getDescription(), offerDBO.getParams(), offerDBO.getPrice());
    }

    @Override
    public OfferDBO convertToDbo(OfferDTO offerDTO) {
        return null;
    }
}
