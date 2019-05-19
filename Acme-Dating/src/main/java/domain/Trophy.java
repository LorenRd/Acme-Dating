package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class Trophy extends DomainEntity {

	private String title;
	private String picture;
	private int scoreToReach;
	private int challengesToComplete;
	private int experiencesToShare;

	@NotBlank
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@URL
	public String getPicture() {
		return this.picture;
	}

	public void setPicture(final String picture) {
		this.picture = picture;
	}

	public int getScoreToReach() {
		return scoreToReach;
	}

	public void setScoreToReach(int scoreToReach) {
		this.scoreToReach = scoreToReach;
	}

	public int getChallengesToComplete() {
		return challengesToComplete;
	}

	public void setChallengesToComplete(int challengesToComplete) {
		this.challengesToComplete = challengesToComplete;
	}

	public int getExperiencesToShare() {
		return experiencesToShare;
	}

	public void setExperiencesToShare(int experiencesToShare) {
		this.experiencesToShare = experiencesToShare;
	}

	// Relationships---------------------------------

	private Collection<Couple> couples;

	@NotNull
	@ManyToMany
	public Collection<Couple> getCouples() {
		return this.couples;
	}

	public void setCouples(final Collection<Couple> couples) {
		this.couples = couples;
	}

}
