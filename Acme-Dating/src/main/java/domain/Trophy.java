package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class Trophy extends DomainEntity {

	private String title;
	private String picture;
	private Integer scoreToReach;
	private Integer challengesToComplete;
	private Integer experiencesToShare;

	@NotBlank
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@NotBlank
	@URL
	public String getPicture() {
		return this.picture;
	}

	public void setPicture(final String picture) {
		this.picture = picture;
	}

	// @NotNull
	// @NotBlank
	// @Range(min = 0)
	// @Pattern(regexp = "[\\s]*[0-9]*[1-9]+", message = ">0   -.-")
	public Integer getScoreToReach() {
		return scoreToReach;
	}

	public void setScoreToReach(int scoreToReach) {
		this.scoreToReach = scoreToReach;
	}

	public Integer getChallengesToComplete() {
		return challengesToComplete;
	}

	public void setChallengesToComplete(int challengesToComplete) {
		this.challengesToComplete = challengesToComplete;
	}

	public Integer getExperiencesToShare() {
		return experiencesToShare;
	}

	public void setExperiencesToShare(int experiencesToShare) {
		this.experiencesToShare = experiencesToShare;
	}

}
