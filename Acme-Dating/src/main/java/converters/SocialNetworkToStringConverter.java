/*
 * ActorToStringConverter.java
 * 
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.SocialNetwork;

@Component
@Transactional
public class SocialNetworkToStringConverter implements Converter<SocialNetwork, String> {

	@Override
	public String convert(final SocialNetwork socialProfile) {
		String result;

		if (socialProfile == null)
			result = null;
		else
			result = String.valueOf(socialProfile.getId());

		return result;
	}
}
