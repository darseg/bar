package gp.training.kim.bar.service;

import gp.training.kim.bar.dbo.OfferDBO;
import gp.training.kim.bar.dto.OfferDTO;
import gp.training.kim.bar.dto.entity.Menu;
import gp.training.kim.bar.exception.BarCannotCreateEntity;
import gp.training.kim.bar.exception.BarOfferIsNotAvailableException;

import java.util.List;
import java.util.Map;

public interface OfferService {
	Menu getMenu();

	void releaseOffers(Map<OfferDBO, Integer> offersForRelease) throws BarOfferIsNotAvailableException;

	List<OfferDTO> getOffersReport();

	OfferDTO addOffer(OfferDTO offerDTO) throws BarCannotCreateEntity;
}
