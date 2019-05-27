package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.TrophyRepository;

import domain.Couple;
import domain.Trophy;

@Service
@Transactional
public class TrophyService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private TrophyRepository trophyRepository;

	// ------------------------------------------------------------------------

	// Supporting services ----------------------------------------------------

	@Autowired
	private CoupleService coupleService;

	@Autowired
	private Validator validator;

	// Simple CRUD Methods

	public Trophy create() {
		Trophy result;
		new Trophy();

		result = new Trophy();
		return result;
	}

	public Trophy findOne(final int trophyId) {
		Trophy result;

		result = this.trophyRepository.findOne(trophyId);
		Assert.notNull(result);
		return result;
	}

	public Collection<Trophy> findAll() {
		Collection<Trophy> result;

		result = this.trophyRepository.findAll();
		Assert.notNull(result);
		return result;
	}

	public Trophy save(final Trophy trophy) {
		Trophy result;

		result = this.trophyRepository.save(trophy);
		Assert.notNull(result);

		return result;
	}

	public void delete(final Trophy trophy) {

		Collection<Couple> couples = this.coupleService.findByTrophyId(trophy
				.getId());

		Assert.notNull(trophy);

		for (final Couple c : couples) {
			Collection<Trophy> trohpies = c.getTrophies();
			trohpies.remove(trophy);
			c.setTrophies(trohpies);
		}
		this.trophyRepository.delete(trophy);
	}

	public Trophy reconstruct(final Trophy trophy, final BindingResult binding) {
		Trophy result;

		if (trophy.getId() == 0) {
			result = trophy;
		} else {
			result = this.trophyRepository.findOne(trophy.getId());

			if (!trophy.getTitle().equals("")) {
				result.setTitle(trophy.getTitle());
			}
			if (!trophy.getPicture().equals("")) {
				result.setPicture(trophy.getPicture());
			}
			if (!trophy.getScoreToReach().equals("")) {
				result.setScoreToReach(trophy.getScoreToReach());
			}
			if (!trophy.getChallengesToComplete().equals("")) {
				result.setChallengesToComplete(trophy.getChallengesToComplete());
			}
			if (!trophy.getExperiencesToShare().equals("")) {
				result.setExperiencesToShare(trophy.getExperiencesToShare());
			}
		}
		this.validator.validate(result, binding);

		return result;
	}

}
