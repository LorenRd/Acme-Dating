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

import domain.Task;

@Component
@Transactional
public class TaskToStringConverter implements Converter<Task, String> {

	@Override
	public String convert(final Task task) {
		String result;

		if (task == null)
			result = null;
		else
			result = String.valueOf(task.getId());

		return result;
	}
}
