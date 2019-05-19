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

import domain.RecordComment;

@Component
@Transactional
public class RecordCommentToStringConverter implements
		Converter<RecordComment, String> {

	@Override
	public String convert(final RecordComment recordComment) {
		String result;

		if (recordComment == null)
			result = null;
		else
			result = String.valueOf(recordComment.getId());

		return result;
	}
}
