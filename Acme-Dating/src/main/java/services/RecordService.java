package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import domain.Category;
import domain.Couple;
import domain.Record;
import repositories.RecordRepository;

@Service
@Transactional
public class RecordService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private RecordRepository recordRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private CoupleService coupleService;

	@Autowired
	private Validator validator;

	// Simple CRUD Methods

	public Record create() {
		Record result;
		final Couple principal;
		Category category = new Category();

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

		Assert.notNull(record);

		principal = this.coupleService.findByUser();
		Assert.notNull(principal);

		Assert.isTrue(record.getCouple().getId() == principal.getId());

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
			result = record;
			result.setCouple(this.coupleService.findByUser());
			result.setCategory(record.getCategory());
		} else {
			result = this.recordRepository.findOne(record.getId());

			if (!record.getTitle().equals("")) {
				result.setTitle(record.getTitle());
			}

			if (!record.getBody().equals("")) {
				result.setBody(record.getBody());
			}

			if (!record.getPhoto().equals("")) {
				result.setPhoto(record.getPhoto());
			}
			if (!record.getDay().equals("")) {
				result.setDay(record.getDay());
			}
			result.setCategory(record.getCategory());
		}

		this.validator.validate(result, binding);

		return result;
	}

}
