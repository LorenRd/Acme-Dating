
package services;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.CoupleRequestRepository;
import domain.Couple;
import domain.CoupleRequest;
import domain.User;

@Service
@Transactional
public class CoupleRequestService {

	// Managed Repository

	@Autowired
	private CoupleRequestRepository	coupleRequestRepository;

	// Supporting services

	@Autowired
	private UserService				userService;

	@Autowired
	private CoupleService			coupleService;

	@Autowired
	private Validator				validator;


	// Simple CRUD methods

	public CoupleRequest create() {
		CoupleRequest result;
		User sender;
		Date moment;

		sender = this.userService.findByPrincipal();
		Assert.notNull(sender);

		result = new CoupleRequest();
		Assert.notNull(result);

		moment = new Date(System.currentTimeMillis() - 1);
		Assert.notNull(moment);

		result.setMoment(moment);
		result.setSender(sender);
		result.setStatus("PENDING");

		return result;
	}

	public CoupleRequest save(final CoupleRequest cR) {

		CoupleRequest result;
		User principal;
		Date moment;

		Assert.notNull(cR);

		principal = this.userService.findByPrincipal();
		Assert.isTrue(cR.getSender().getId() == principal.getId());
		Assert.isNull(this.findBySenderIdAndRecipientId(principal.getId(), cR.getRecipient().getId()));

		moment = new Date(System.currentTimeMillis() - 1);
		Assert.notNull(moment);
		cR.setMoment(moment);

		result = this.coupleRequestRepository.save(cR);

		return result;
	}

	public void reject(final CoupleRequest cR) {
		User principal;

		Assert.notNull(cR);
		Assert.isTrue(cR.getId() != 0);

		principal = this.userService.findByPrincipal();
		Assert.notNull(principal);

		Assert.isTrue(cR.getStatus().equals("PENDING"));

		this.coupleRequestRepository.delete(cR);
	}

	public void accept(final CoupleRequest cR) {
		User principal;
		Couple couple;
		Collection<CoupleRequest> noAcceptedRequests;
		CoupleRequest savedCR;

		Assert.notNull(cR);
		Assert.isTrue(cR.getId() != 0);

		principal = this.userService.findByPrincipal();
		Assert.notNull(principal);

		Assert.isTrue(cR.getStatus().equals("PENDING"));
		Assert.isTrue(principal.getId() == cR.getRecipient().getId());

		cR.setStatus("ACCEPTED");

		couple = this.coupleService.create(cR.getSender(), cR.getRecipient());
		couple = this.coupleService.save(couple, cR.getSender(), cR.getRecipient());

		savedCR = this.coupleRequestRepository.save(cR);

		noAcceptedRequests = this.findCoupleRequestsByRecipientId(savedCR.getRecipient().getId());
		noAcceptedRequests.addAll(this.findCoupleRequestsByRecipientId(savedCR.getSender().getId()));
		noAcceptedRequests.remove(savedCR);
		for (final CoupleRequest nAR : noAcceptedRequests)
			this.coupleRequestRepository.delete(nAR);
	}

	public CoupleRequest findOne(final int cRId) {
		CoupleRequest result;

		result = this.coupleRequestRepository.findOne(cRId);
		Assert.notNull(result);
		return result;
	}

	public Collection<CoupleRequest> findAll() {
		Collection<CoupleRequest> result;

		result = this.coupleRequestRepository.findAll();
		Assert.notNull(result);
		return result;
	}

	public CoupleRequest findBySenderIdAndRecipientId(final int senderId, final int recipientId) {
		CoupleRequest result;

		result = this.coupleRequestRepository.findBySenderIdAndRecipientId(senderId, recipientId);

		return result;
	}

	public Collection<CoupleRequest> findCoupleRequestsByRecipientId(final int userId) {
		Collection<CoupleRequest> result;

		result = this.coupleRequestRepository.findCoupleRequestsByRecipientId(userId);

		return result;
	}

	public Collection<CoupleRequest> findAllCoupleRequestsByUserId(final int userId) {
		Collection<CoupleRequest> result;
		result = this.coupleRequestRepository.findAllCoupleRequestsByUserId(userId);
		return result;
	}

	public CoupleRequest findCoupleRequestsBySenderId(final int userId) {
		CoupleRequest result;

		result = this.coupleRequestRepository.findCoupleRequestsBySenderId(userId);

		return result;
	}

	public CoupleRequest reconstruct(final CoupleRequest cR, final BindingResult binding) {
		CoupleRequest result;
		if (cR.getId() == 0)
			result = cR;
		else
			result = this.coupleRequestRepository.findOne(cR.getId());

		result.setSender(this.userService.findByPrincipal());
		result.setMoment(cR.getMoment());
		result.setStatus(cR.getStatus());
		result.setRecipient(cR.getRecipient());

		this.validator.validate(result, binding);

		return result;
	}

	public void deleteInBach(final Collection<CoupleRequest> coupleRequests) {
		this.coupleRequestRepository.deleteInBatch(coupleRequests);
	}
}
