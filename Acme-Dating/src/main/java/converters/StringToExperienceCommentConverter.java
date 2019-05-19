
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.ExperienceCommentRepository;
import domain.ExperienceComment;

@Component
@Transactional
public class StringToExperienceCommentConverter implements Converter<String, ExperienceComment> {

	@Autowired
	ExperienceCommentRepository	experienceCommentRepository;


	@Override
	public ExperienceComment convert(final String text) {
		ExperienceComment result;
		int id;

		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				result = this.experienceCommentRepository.findOne(id);
			}
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}
		return result;
	}
}
