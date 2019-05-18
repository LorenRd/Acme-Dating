
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.SocialNetworkRepository;
import domain.SocialNetwork;

@Component
@Transactional
public class StringToSocialNetworkConverter implements Converter<String, SocialNetwork> {

	@Autowired
	SocialNetworkRepository	socialNetworkRepository;


	@Override
	public SocialNetwork convert(final String text) {
		SocialNetwork result;
		int id;

		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				result = this.socialNetworkRepository.findOne(id);
			}
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}
		return result;
	}
}
