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

import domain.Diary;

@Component
@Transactional
public class DiaryToStringConverter implements Converter<Diary, String> {

	@Override
	public String convert(final Diary diary) {
		String result;

		if (diary == null)
			result = null;
		else
			result = String.valueOf(diary.getId());

		return result;
	}
}
