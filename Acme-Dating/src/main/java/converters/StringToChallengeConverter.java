
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.ChallengeRepository;
import domain.Challenge;

@Component
@Transactional
public class StringToChallengeConverter implements Converter<String, Challenge> {

	@Autowired
	ChallengeRepository	challengeRepository;


	@Override
	public Challenge convert(final String text) {
		Challenge result;
		int id;

		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				result = this.challengeRepository.findOne(id);
			}
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}
		return result;
	}
}
