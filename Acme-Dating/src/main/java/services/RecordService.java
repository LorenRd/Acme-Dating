
package services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.RecordCommentRepository;
import repositories.RecordRepository;
import domain.Category;
import domain.Couple;
import domain.Record;
import domain.RecordComment;

@Service
@Transactional
public class RecordService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private RecordRepository		recordRepository;

	@Autowired
	private RecordCommentRepository	recordCommentRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private CoupleService coupleService;

	@Autowired
	private Validator				validator;


	// Simple CRUD Methods

	public Record create() {
		Record result;
		final Couple principal;
		final Category category = new Category();

		principal = this.coupleService.findByUser();
		Assert.notNull(principal);

		result = new Record();
		result.setCouple(principal);
		result.setCategory(category);
		return result;
	}

	public Record save(final Record record) {
		Couple principal;
		Record result;

		principal = this.coupleService.findByUser();
		Assert.notNull(principal);

		Assert.notNull(record);
		Assert.isTrue(record.getCouple() == principal);

		result = this.recordRepository.save(record);
		Assert.notNull(result);

		return result;
	}

	public Record findOne(final int recordId) {
		Record result;

		result = this.recordRepository.findOne(recordId);
		Assert.notNull(result);
		return result;
	}

	public void delete(final Record record) {
		Couple principal;
		final Collection<RecordComment> recordComments = this.recordCommentRepository.findByRecordId(record.getId());

		Assert.notNull(record);

		principal = this.coupleService.findByUser();
		Assert.notNull(principal);

		Assert.isTrue(record.getCouple().getId() == principal.getId());
		
		for (RecordComment rC : recordComments) {
			if(rC.getRecord()!=null){
				Collection<RecordComment> childs = new ArrayList<RecordComment>();
				childs = this.recordCommentRepository.findChilds(rC.getId());
				for (RecordComment rCC : childs) {
					this.recordCommentRepository.delete(rCC);
				}
				this.recordCommentRepository.delete(rC);
			}
		}
		this.recordRepository.delete(record);
	}

	// Additional functions

	public Collection<Record> findByCoupleId(final int coupleId) {
		Collection<Record> result;

		result = this.recordRepository.findByCoupleId(coupleId);
		return result;
	}

	public Record reconstruct(final Record record, final BindingResult binding) {
		Record result;
		Date dt = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(dt);
		c.add(Calendar.DATE, 1);

		if (record.getId() == 0) {
			result = record;
		} else {
			result = this.recordRepository.findOne(record.getId());
		}
		result.setCouple(this.coupleService.findByUser());
		result.setDay(record.getDay());
		if(record.getTitle()!=null)
			result.setTitle(record.getTitle());
		if(record.getBody()!= null)
			result.setBody(record.getBody());
		result.setPhoto(record.getPhoto());
		result.setCategory(record.getCategory());
		
		if(record.getDay() != null){
			if (record.getDay().after(dt)) {
				binding.rejectValue("day", "record.validation.day", "Date must be past");
			}
		}
		this.validator.validate(result, binding);

		return result;
	}

}
