
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.CoupleRequestRepository;
import domain.CoupleRequest;

@Component
@Transactional
public class StringToCoupleRequestConverter implements Converter<String, CoupleRequest> {

	@Autowired
	CoupleRequestRepository	coupleRequestRepository;


	@Override
	public CoupleRequest convert(final String text) {
		CoupleRequest result;
		int id;

		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				result = this.coupleRequestRepository.findOne(id);
			}
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}
		return result;
	}
}
