package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.RecordCommentRepository;
import domain.RecordComment;

@Component
@Transactional
public class StringToRecordCommentConverter implements
		Converter<String, RecordComment> {

	@Autowired
	RecordCommentRepository recordCommentRepository;

	@Override
	public RecordComment convert(final String text) {
		RecordComment result;
		int id;

		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				result = this.recordCommentRepository.findOne(id);
			}
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}
		return result;
	}
}
