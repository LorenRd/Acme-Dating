
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.DiaryRepository;
import domain.Diary;

@Component
@Transactional
public class StringToDiaryConverter implements Converter<String, Diary> {

	@Autowired
	DiaryRepository	diaryRepository;


	@Override
	public Diary convert(final String text) {
		Diary result;
		int id;

		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				result = this.diaryRepository.findOne(id);
			}
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}
		return result;
	}
}
