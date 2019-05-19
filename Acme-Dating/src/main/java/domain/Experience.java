package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.URL;

import cz.jirutka.validator.collection.constraints.EachNotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Experience extends DomainEntity{

	private String 	title;
	private String 	body;
	private String 	photo;
	private String 	ubication;
	private double    	score;
	private Collection<String> comments;
	private double 	prize;
	private int 	coupleLimit;
	

	@NotBlank
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	@NotBlank
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}	
	
	@URL
	public String getPhoto() {
		return this.photo;
	}

	public void setPhoto(final String photo) {
		this.photo = photo;
	}
	
	@NotBlank
	public String getUbication() {
		return ubication;
	}
	public void setUbication(String ubication) {
		this.ubication = ubication;
	}
	
	@Range(min = 0, max = 5)
	public double getScore() {
		return score;
	}
	public void setScore(double score) {
		this.score = score;
	}
	
	@ElementCollection
	@EachNotBlank
	public Collection<String> getComments() {
		return comments;
	}
	public void setComments(Collection<String> comments) {
		this.comments = comments;
	}
	
	@NotNull
	public double getPrize() {
		return prize;
	}
	public void setPrize(double prize) {
		this.prize = prize;
	}
	
	@NotNull
	public int getCoupleLimit() {
		return coupleLimit;
	}
	public void setCoupleLimit(int coupleLimit) {
		this.coupleLimit = coupleLimit;
	}




	// Relationships----------------------------------------------
	private Category category;
	private Company	company;
	private Collection<Feature> features;
	
	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Category getCategory() {
		return this.category;
	}

	public void setCategory(final Category category) {
		this.category = category;
	}
	
	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Company getCompany() {
		return this.company;
	}

	public void setCompany(final Company company) {
		this.company = company;
	}
	
	@Valid
	@ManyToMany
	public Collection<Feature> getFeatures() {
		return features;
	}
	public void setFeatures(Collection<Feature> features) {
		this.features = features;
	}
	
	

}
