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

import domain.MessageBox;

@Component
@Transactional
public class MessageBoxToStringConverter implements Converter<MessageBox, String> {

	@Override
	public String convert(final MessageBox messageBox) {
		String result;

		if (messageBox == null)
			result = null;
		else
			result = String.valueOf(messageBox.getId());

		return result;
	}
}
