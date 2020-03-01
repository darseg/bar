package gp.training.kim.bar.service;

import gp.training.kim.bar.dbo.OfferDBO;
import gp.training.kim.bar.dto.OfferDTO;
import gp.training.kim.bar.dto.entity.Menu;
import gp.training.kim.bar.exception.OfferIsNotAvailableException;

import java.util.List;
import java.util.Map;

public interface OfferService {
	Menu getMenu();

	void releaseOffers(Map<OfferDBO, Integer> offersForRelease) throws OfferIsNotAvailableException;

	List<OfferDTO> getOffersReport();
}
