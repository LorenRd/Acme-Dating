package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Entity
@Access(AccessType.PROPERTY)
public class User extends Actor {

	private String email;
	private boolean inRelationship;

	@Pattern(regexp = "^[a-zA-Z0-9 ]*[<]?\\w+[@][a-zA-Z0-9.]+[>]?$")
	public String getEmail() {
		return this.email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	public boolean getInRelationship() {
		return this.inRelationship;
	}

	public void setInRelationship(boolean inRelationship) {
		this.inRelationship = inRelationship;
	}
	
	// Relationships----------------------------------------------
	
	private Collection<SocialProfile>	socialProfiles;
	private Couple 						couple;

	@NotNull
	@OneToMany
	public Collection<SocialProfile> getSocialProfiles() {
		return this.socialProfiles;
	}

	public void setSocialProfiles(final Collection<SocialProfile> socialProfiles) {
		this.socialProfiles = socialProfiles;
	}

	@Valid
	@ManyToOne(optional = true, cascade = CascadeType.ALL)
	public Couple getCouple() {
		return couple;
	}

	public void setCouple(Couple couple) {
		this.couple = couple;
	}

}
