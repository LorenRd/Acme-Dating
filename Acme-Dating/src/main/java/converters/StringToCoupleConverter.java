
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.CoupleRepository;
import domain.Couple;

@Component
@Transactional
public class StringToCoupleConverter implements Converter<String, Couple> {

	@Autowired
	CoupleRepository	coupleRepository;


	@Override
	public Couple convert(final String text) {
		Couple result;
		int id;

		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				result = this.coupleRepository.findOne(id);
			}
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}
		return result;
	}
}
