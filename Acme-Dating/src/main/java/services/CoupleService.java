
package services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.CoupleRepository;
import domain.Couple;
import domain.Diary;
import domain.User;

@Service
@Transactional
public class CoupleService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private CoupleRepository	coupleRepository;

	// Supporting services ----------------------------------------------------
	@Autowired
	private UserService			userService;

	@Autowired
	private DiaryService		diaryService;


	public Couple create(final User sender, final User recipient) {
		Couple result;
		Date startDate;
		Diary diary;

		Assert.notNull(sender);
		Assert.notNull(recipient);

		result = new Couple();
		diary = this.diaryService.create();
		startDate = new Date(System.currentTimeMillis() - 1);

		result.setScore(0);
		result.setDiary(diary);
		result.setStartDate(startDate);

		sender.setCouple(result);
		recipient.setCouple(result);

		return result;
	}

	public Couple save(final Couple couple, final User sender, final User recipient) {
		Couple result;
		Date startDate;
		Diary diary;
		Diary savedDiary;

		Assert.notNull(couple);

		startDate = new Date(System.currentTimeMillis() - 1);
		couple.setStartDate(startDate);

		diary = couple.getDiary();
		savedDiary = this.diaryService.save(diary);
		couple.setDiary(savedDiary);

		result = this.coupleRepository.save(couple);

		sender.setCouple(result);
		recipient.setCouple(result);

		this.userService.save(sender);
		this.userService.save(recipient);

		Assert.notNull(result);

		return result;
	}

	public Couple findByUser() {
		User principal;

		principal = this.userService.findByPrincipal();
		Assert.notNull(principal);

		return principal.getCouple();

		return principal.getCouple();
	}

}
