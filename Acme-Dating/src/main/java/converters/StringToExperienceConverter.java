
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.ExperienceRepository;
import domain.Experience;

@Component
@Transactional
public class StringToExperienceConverter implements Converter<String, Experience> {

	@Autowired
	ExperienceRepository	experienceRepository;


	@Override
	public Experience convert(final String text) {
		Experience result;
		int id;

		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				result = this.experienceRepository.findOne(id);
			}
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}
		return result;
	}
}
