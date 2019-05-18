
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.FeatureRepository;
import domain.Feature;

@Component
@Transactional
public class StringToFeatureConverter implements Converter<String, Feature> {

	@Autowired
	FeatureRepository	featureRepository;


	@Override
	public Feature convert(final String text) {
		Feature result;
		int id;

		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				result = this.featureRepository.findOne(id);
			}
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}
		return result;
	}
}
