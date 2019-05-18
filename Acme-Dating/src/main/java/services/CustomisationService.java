
package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.CustomisationRepository;
import domain.Customisation;

@Service
@Transactional
public class CustomisationService {

	// Managed Repository
	@Autowired
	private CustomisationRepository	customisationRepository;

	// Supporting services

	public Customisation find() {
		Customisation result;

		result = this.customisationRepository.findAll().get(0);
		Assert.notNull(result);

		return result;

	}
}
