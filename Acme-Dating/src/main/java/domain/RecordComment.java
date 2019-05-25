package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class RecordComment extends DomainEntity {

	private String body;

	@NotBlank
	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	// Relationships----------------------------------------------

	private Record record;
	private RecordComment recordComment;

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Record getRecord() {
		return this.record;
	}

	public void setRecord(final Record record) {
		this.record = record;
	}

	@Valid
	@OneToOne(optional = true)
	public RecordComment getRecordComment() {
		return this.recordComment;
	}

	public void setRecordComment(final RecordComment recordComment) {
		this.recordComment = recordComment;
	}

}
