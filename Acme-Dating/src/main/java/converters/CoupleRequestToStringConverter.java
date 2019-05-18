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

import domain.CoupleRequest;

@Component
@Transactional
public class CoupleRequestToStringConverter implements Converter<CoupleRequest, String> {

	@Override
	public String convert(final CoupleRequest coupleRequest) {
		String result;

		if (coupleRequest == null)
			result = null;
		else
			result = String.valueOf(coupleRequest.getId());

		return result;
	}
}
