package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class ExperienceComment extends DomainEntity {

	private String body;

	@NotBlank
	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	// Relationships----------------------------------------------

	private Experience experience;
	private ExperienceComment experienceComment;
	private Actor actor;

	@Valid
	@ManyToOne(optional = true)
	public Experience getExperience() {
		return this.experience;
	}

	public void setExperience(final Experience experience) {
		this.experience = experience;
	}

	@Valid
	@OneToOne(optional = true)
	public ExperienceComment getExperienceComment() {
		return this.experienceComment;
	}

	public void setExperienceComment(final ExperienceComment experienceComment) {
		this.experienceComment = experienceComment;
	}

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Actor getActor() {
		return this.actor;
	}

	public void setActor(final Actor actor) {
		this.actor = actor;
	}
}
