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
	
	private Collection<SocialNetwork>	socialProfiles;
	private Couple 						couple;

	@NotNull
	@OneToMany
	public Collection<SocialNetwork> getSocialProfiles() {
		return this.socialProfiles;
	}

	public void setSocialProfiles(final Collection<SocialNetwork> socialProfiles) {
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
