package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.Valid;

@Entity
@Access(AccessType.PROPERTY)
public class Diary extends DomainEntity{
	// Relationships----------------------------------------------
	private Collection<Record>		records;
	
	@Valid
	@OneToMany
	public Collection<Record> getRecords() {
		return records;
	}

	public void setTasks(Collection<Record> records) {
		this.records = records;
	}
	
}
