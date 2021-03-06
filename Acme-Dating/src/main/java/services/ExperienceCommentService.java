
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.ExperienceCommentRepository;
import domain.Actor;
import domain.Experience;
import domain.ExperienceComment;

@Service
@Transactional
public class ExperienceCommentService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private ExperienceCommentRepository	experienceCommentRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private ActorService				actorService;

	@Autowired
	private Validator					validator;

	@Autowired
	private ExperienceService			experienceService;


	// Simple CRUD Methods

	public ExperienceComment create(final boolean isFather, final int id) {
		ExperienceComment result;
		final Actor principal;

		principal = this.actorService.findByPrincipal();
		Assert.notNull(principal);

		result = new ExperienceComment();
		result.setActor(principal);
		if (isFather) {
			Experience experience;
			experience = this.experienceService.findOne(id);
			result.setExperience(experience);
		} else {
			ExperienceComment experienceComment;
			experienceComment = this.findOne(id);
			result.setExperienceComment(experienceComment);
		}

		return result;
	}

	public boolean exist(final Integer experienceCommentId) {
		return this.experienceCommentRepository.exists(experienceCommentId);
	}

	public ExperienceComment findOne(final int experienceCommentId) {
		ExperienceComment result;

		result = this.experienceCommentRepository.findOne(experienceCommentId);
		Assert.notNull(result);
		return result;
	}

	public Collection<ExperienceComment> findAll() {
		Collection<ExperienceComment> result;

		result = this.experienceCommentRepository.findAll();
		Assert.notNull(result);
		return result;
	}

	public Collection<ExperienceComment> findChilds(final int experienceCommentFatherId) {
		Collection<ExperienceComment> result;

		result = this.experienceCommentRepository.findChilds(experienceCommentFatherId);

		return result;
	}
	public ExperienceComment save(final ExperienceComment experienceComment) {
		ExperienceComment result;

		result = this.experienceCommentRepository.save(experienceComment);
		Assert.notNull(result);
		return result;
	}

	public ExperienceComment reconstruct(final ExperienceComment experienceComment, final boolean isFather, final int id, final BindingResult binding) {
		ExperienceComment result;
		Experience experience;

		result = experienceComment;
		result.setActor(this.actorService.findByPrincipal());
		if (isFather) {
			experience = this.experienceService.findOne(id);
			result.setExperience(experience);
		} else
			result.setExperienceComment(this.findOne(id));
		result.setBody(experienceComment.getBody());

		this.validator.validate(result, binding);
		return result;
	}

	public void delete(final ExperienceComment experienceComment) {
		this.experienceCommentRepository.delete(experienceComment);
	}
	// Business Methods

	public Collection<ExperienceComment> findByExperienceId(final int experienceId) {
		Collection<ExperienceComment> result;

		result = this.experienceCommentRepository.findByExperienceId(experienceId);
		Collection<ExperienceComment> childs = new ArrayList<ExperienceComment>();

		for (final ExperienceComment eC : result) {
			childs = new ArrayList<ExperienceComment>();
			childs.addAll(this.experienceCommentRepository.findChilds(eC.getId()));
		}
		result.addAll(childs);
		return result;
	}

	public void flush() {
		this.experienceCommentRepository.flush();
	}

}
