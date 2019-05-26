
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Challenge extends DomainEntity {

	private Date	moment;
	private String	title;
	private String	description;
	private int		score;
	private Date	endDate;
	private String	status;


	@NotNull
	@Past
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	@Temporal(TemporalType.TIMESTAMP)
	public Date getMoment() {
		return this.moment;
	}

	public void setMoment(final Date moment) {
		this.moment = moment;
	}

	@NotBlank
	public String getTitle() {
		return this.title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	@NotBlank
	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	@Range(min = 0, max = 100)
	public int getScore() {
		return this.score;
	}

	public void setScore(final int score) {
		this.score = score;
	}

	@NotNull
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	@Temporal(TemporalType.TIMESTAMP)
	public Date getEndDate() {
		return this.endDate;
	}

	public void setEndDate(final Date endDate) {
		this.endDate = endDate;
	}

	@NotBlank
	@Pattern(regexp = "^PENDING|ACCEPTED|COMPLETED$")
	public String getStatus() {
		return this.status;
	}
	public void setStatus(final String status) {
		this.status = status;
	}


	// Relationships----------------------------------------------

	private User	sender;
	private User	recipient;


	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public User getSender() {
		return this.sender;
	}

	public void setSender(final User sender) {
		this.sender = sender;
	}

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public User getRecipient() {
		return this.recipient;
	}

	public void setRecipient(final User recipient) {
		this.recipient = recipient;
	}

}
