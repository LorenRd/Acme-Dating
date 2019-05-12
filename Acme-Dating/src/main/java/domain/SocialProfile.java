package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class SocialProfile extends DomainEntity {

	private String socialNetwork;
	private String linkProfile;

	@NotBlank
	public String getSocialNetwork() {
		return this.socialNetwork;
	}

	public void setSocialNetwork(final String socialNetwork) {
		this.socialNetwork = socialNetwork;
	}

	@NotBlank
	@URL
	public String getLinkProfile() {
		return this.linkProfile;
	}

	public void setLinkProfile(final String linkProfile) {
		this.linkProfile = linkProfile;
	}
}
