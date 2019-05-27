package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.TrophyRepository;

import domain.Trophy;

@Service
@Transactional
public class TrophyService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private TrophyRepository trophyRepository;

	// ------------------------------------------------------------------------

	public Collection<Trophy> findAll() {
		Collection<Trophy> result;

		result = this.trophyRepository.findAll();
		Assert.notNull(result);
		return result;
	}

}
