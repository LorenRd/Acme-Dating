package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import domain.RecordComment;

import repositories.RecordCommentRepository;

@Service
@Transactional
public class RecordCommentService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private RecordCommentRepository recordCommentRepository;

	// Simple CRUD Methods
	public void delete(final RecordComment recordComment) {
		this.recordCommentRepository.delete(recordComment);
	}
}
