package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.RecordCommentRepository;
import repositories.RecordRepository;
import repositories.UserRepository;
import security.LoginService;
import security.UserAccount;
import domain.Category;
import domain.Couple;
import domain.Record;
import domain.RecordComment;
import domain.User;

@Service
@Transactional
public class RecordService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private RecordRepository recordRepository;

	@Autowired
	private RecordCommentRepository recordCommentRepository;

	@Autowired
	private UserRepository userRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private CoupleService coupleService;

	@Autowired
	private Validator validator;

	// Simple CRUD Methods

	public Record create() {
		Record result;
		final Category category = new Category();

		result = new Record();
		result.setCategory(category);
		return result;
	}

	public Record save(final Record record) {
		Record result;

		Assert.notNull(record);
		Assert.isTrue(this.owner(record));

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
		final Collection<RecordComment> recordComments = this.recordCommentRepository
				.findByRecordId(record.getId());

		Assert.notNull(record);

		principal = this.coupleService.findByUser();
		Assert.notNull(principal);

		Assert.isTrue(record.getCouple().getId() == principal.getId());

		for (RecordComment rC : recordComments) {
			if (rC.getRecord() != null) {
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

		if (record.getId() == 0) {
			final Couple principal = this.coupleService.findByUser();
			Assert.notNull(principal);
			record.setCouple(principal);
			result = record;
		} else {
			result = this.recordRepository.findOne(record.getId());
			result.setCouple(this.coupleService.findByUser());
			result.setDay(record.getDay());
			result.setTitle(record.getTitle());
			result.setBody(record.getBody());
			result.setPhoto(record.getPhoto());
			result.setCategory(record.getCategory());
		}

		this.validator.validate(result, binding);

		if (record.getDay().after(new Date())) {
			binding.rejectValue("day", "record.validation.day",
					"Date must be past");
		}

		if (binding.hasErrors())
			throw new ValidationException();

		return result;
	}

	public boolean owner(Record record) {
		boolean result = true;

		UserAccount userAccountPrincipal = LoginService.getPrincipal();
		User principal = this.userRepository
				.findByUserAccountId(userAccountPrincipal.getId());

		if (record.getCouple() != principal.getCouple()) {
			result = false;
		}
		return result;
	}

}
