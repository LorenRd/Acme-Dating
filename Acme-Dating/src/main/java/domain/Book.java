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

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Book extends DomainEntity{
	
	private Date	moment;	
	private Date 	date;
	
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
	
	@NotNull
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	@Temporal(TemporalType.TIMESTAMP)
	public Date getDate() {
		return this.date;
	}

	public void setDate(final Date date) {
		this.date = date;
	}
	
	// Relationships----------------------------------------------

	
	private Couple couple;
	private Experience experience;
	
	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Couple getCouple() {
		return this.couple;
	}

	public void setCouple(final Couple couple) {
		this.couple = couple;
	}
	
	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Experience getExperience() {
		return this.experience;
	}

	public void setExperience(final Experience experience) {
		this.experience = experience;
	}
}
