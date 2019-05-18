
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.TrophyRepository;
import domain.Trophy;

@Component
@Transactional
public class StringToTrophyConverter implements Converter<String, Trophy> {

	@Autowired
	TrophyRepository	trophyRepository;


	@Override
	public Trophy convert(final String text) {
		Trophy result;
		int id;

		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				result = this.trophyRepository.findOne(id);
			}
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}
		return result;
	}
}
