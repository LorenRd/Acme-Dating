package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.URL;

import cz.jirutka.validator.collection.constraints.EachNotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Customisation extends DomainEntity {

	private String welcomeBanner;
	private String welcomeMessageEn;
	private String welcomeMessageEs;
	private String countryCode;
	private Double vatNumber;
	private Collection<String> scoreWords;

	@NotBlank
	@URL
	public String getWelcomeBanner() {
		return this.welcomeBanner;
	}

	public void setWelcomeBanner(final String welcomeBanner) {
		this.welcomeBanner = welcomeBanner;
	}

	@NotBlank
	public String getWelcomeMessageEn() {
		return this.welcomeMessageEn;
	}

	public void setWelcomeMessageEn(final String welcomeMessageEn) {
		this.welcomeMessageEn = welcomeMessageEn;
	}

	@NotBlank
	public String getWelcomeMessageEs() {
		return this.welcomeMessageEs;
	}

	public void setWelcomeMessageEs(final String welcomeMessageEs) {
		this.welcomeMessageEs = welcomeMessageEs;
	}

	@Pattern(regexp = "^\\+?\\d{1,3}$")
	public String getCountryCode() {
		return this.countryCode;
	}

	public void setCountryCode(final String countryCode) {
		this.countryCode = countryCode;
	}

	@Range(min = 0, max = 100)
	public Double getVatNumber() {
		return vatNumber;
	}

	public void setVatNumber(Double vatNumber) {
		this.vatNumber = vatNumber;
	}

	@ElementCollection
	@EachNotBlank
	public Collection<String> getScoreWords() {
		return scoreWords;
	}

	public void setScoreWords(Collection<String> scoreWords) {
		this.scoreWords = scoreWords;
	}
}
