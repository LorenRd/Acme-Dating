package domain;

import java.util.Date;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class CoupleRequest extends DomainEntity {

	private Date moment;
	private String status;

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
	@Pattern(regexp = "^PENDING|ACCEPTED$")
	public String getStatus() {
		return this.status;
	}

	public void setStatus(final String status) {
		this.status = status;
	}

	// Relationships----------------------------------------------

	private User sender;
	private User recipient;

	@NotNull
	@Valid
	@OneToOne(optional = false)
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
