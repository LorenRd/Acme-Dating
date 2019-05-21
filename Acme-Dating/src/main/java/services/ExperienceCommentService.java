
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ExperienceCommentRepository;
import domain.ExperienceComment;

@Service
@Transactional
public class ExperienceCommentService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private ExperienceCommentRepository	experienceCommentRepository;


	// Supporting services ----------------------------------------------------

	// Simple CRUD Methods

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

	// Business Methods

	public Collection<ExperienceComment> findByExperienceId(final int experienceId) {
		Collection<ExperienceComment> result;

		result = this.experienceCommentRepository.findByExperienceId(experienceId);
		return result;
	}

	public void flush() {
		this.experienceCommentRepository.flush();
	}

}
