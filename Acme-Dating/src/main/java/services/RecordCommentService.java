package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import domain.Record;
import domain.RecordComment;

import repositories.RecordCommentRepository;

@Service
@Transactional
public class RecordCommentService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private RecordCommentRepository recordCommentRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private Validator validator;

	@Autowired
	private RecordService recordService;

	// Simple CRUD Methods
	public RecordComment create(final boolean isFather, final int id) {
		RecordComment result;

		result = new RecordComment();
		if (isFather) {
			Record record;
			record = this.recordService.findOne(id);
			result.setRecord(record);
		} else {
			RecordComment recordComment;
			recordComment = this.findOne(id);
			result.setRecordComment(recordComment);
		}

		return result;
	}

	public boolean exist(final Integer recordCommentId) {
		return this.recordCommentRepository.exists(recordCommentId);
	}

	public RecordComment findOne(final int recordCommentId) {
		RecordComment result;

		result = this.recordCommentRepository.findOne(recordCommentId);
		Assert.notNull(result);
		return result;
	}

	public Collection<RecordComment> findAll() {
		Collection<RecordComment> result;

		result = this.recordCommentRepository.findAll();
		Assert.notNull(result);
		return result;
	}

	public RecordComment save(final RecordComment recordComment) {
		RecordComment result;

		result = this.recordCommentRepository.save(recordComment);
		Assert.notNull(result);
		return result;
	}
	
	public RecordComment reconstruct(final RecordComment recordComment,boolean isFather, final int id, final BindingResult binding) {
		RecordComment result;
		Record record;

		result = recordComment;
		if(isFather){
			record = this.recordService.findOne(id);
			result.setRecord(record);
		}else{
			result.setRecordComment(this.findOne(id));

		}
		result.setBody(recordComment.getBody());

		this.validator.validate(result, binding);
		return result;
	}

	// Business Methods

	public Collection<RecordComment> findByRecordId(final int recordId) {
		Collection<RecordComment> result;

		result = this.recordCommentRepository.findByRecordId(recordId);
		Collection<RecordComment> childs = new ArrayList<RecordComment>();

		for (RecordComment rC : result) {
			childs = new ArrayList<RecordComment>();
			childs.addAll(this.recordCommentRepository.findChilds(rC.getId()));
		}
		result.addAll(childs);
		return result;
	}

	public void flush() {
		this.recordCommentRepository.flush();
	}

}
