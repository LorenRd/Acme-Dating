
package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.DiaryRepository;
import domain.Diary;

@Service
@Transactional
public class DiaryService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private DiaryRepository	diaryRepository;


	// Supporting services ----------------------------------------------------

	public Diary create() {
		Diary result;

		result = new Diary();

		return result;
	}

	public Diary save(final Diary diary) {
		Diary result;

		Assert.notNull(diary);

		result = this.diaryRepository.save(diary);

		return result;
	}
}
