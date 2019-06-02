package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.TrophyRepository;

import domain.Administrator;
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
	private AdministratorService administratorService;
	
	@Autowired
	private Validator validator;

	// Simple CRUD Methods

	public Trophy create() {
		Trophy result;
		Administrator principal;
		
		principal = this.administratorService.findByPrincipal();
		Assert.notNull(principal);
		
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
		}
		result.setTitle(trophy.getTitle());
		result.setPicture(trophy.getPicture());
		result.setScoreToReach(trophy.getScoreToReach());
		result.setChallengesToComplete(trophy.getChallengesToComplete());
		result.setExperiencesToShare(trophy.getExperiencesToShare());

		
		
		if ((trophy.getScoreToReach() < 0)) {
			binding.rejectValue("scoreToReach", "trophy.validation.scoreToReach", "Score to reach must be a positive number");
		}
		if ((trophy.getChallengesToComplete() < 0)) {
			binding.rejectValue("challengesToComplete", "trophy.validation.challengesToComplete", "Challenges to complete must be a positive number");
		}
		if ((trophy.getExperiencesToShare() < 0)) {
			binding.rejectValue("experiencesToShare","trophy.validation.experiencesToShare", "Experiences to share must be a positive number");
		}
		this.validator.validate(result, binding);

		return result;
	}

	public void flush() {
		this.trophyRepository.flush();
	}

}
