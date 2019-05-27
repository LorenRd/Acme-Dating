
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.MessageBoxRepository;
import domain.Actor;
import domain.Message;
import domain.MessageBox;

@Service
@Transactional
public class MessageBoxService {

	// Managed Repository

	@Autowired
	private MessageBoxRepository	messageBoxRepository;

	// Supporting services

	@Autowired
	private ActorService			actorService;


	// Simple CRUD methods

	public MessageBox save(final MessageBox box) {
		MessageBox result;
		Actor principal;
		Collection<Message> messages;
		List<MessageBox> boxes;

		principal = this.actorService.findByPrincipal();
		Assert.notNull(principal);

		result = this.messageBoxRepository.save(box);
		Assert.notNull(result);

		if (box.getId() == 0) {

			messages = new ArrayList<Message>();
			box.setMessages(messages);

		} else
			Assert.isTrue(principal.getMessageBoxes().contains(box));

		if (!principal.getMessageBoxes().contains(result)) {
			boxes = principal.getMessageBoxes();
			boxes.add(result);
			principal.setMessageBoxes(boxes);
			this.actorService.save(principal);
		}

		return result;
	}

	public void save2(final MessageBox box, final Actor actor) {
		MessageBox result;

		Assert.notNull(actor);
		Assert.isTrue(actor.getMessageBoxes().contains(box));

		result = this.messageBoxRepository.save(box);
		Assert.notNull(result);
	}

	public List<MessageBox> createSystemBoxes(final Actor actor) {

		List<MessageBox> result;
		Collection<String> names;
		Collection<Message> messages;
		MessageBox saved;

		names = new ArrayList<String>();
		names.add("in box");
		names.add("out box");
		names.add("notification box");

		result = new ArrayList<MessageBox>();
		for (final String name : names) {
			final MessageBox box = new MessageBox();
			box.setName(name);
			messages = new ArrayList<Message>();
			box.setMessages(messages);
			box.setActor(actor);
			saved = this.messageBoxRepository.save(box);
			Assert.notNull(saved);

			result.add(saved);
		}

		return result;
	}

	public MessageBox findOne(final int boxId) {
		MessageBox result;
		Actor principal;
		principal = this.actorService.findByPrincipal();
		Assert.notNull(principal);
		result = this.messageBoxRepository.findOne(boxId);
		Assert.notNull(result);
		return result;
	}

	// Other business methods

	public MessageBox findInBoxActor(final Actor a) {
		Actor principal;
		MessageBox result;

		principal = this.actorService.findByPrincipal();

		Assert.notNull(principal);
		Assert.notNull(a);
		Assert.isTrue(a.getId() != 0);

		result = this.messageBoxRepository.findInBoxMessageBoxByActorId(a.getId());
		Assert.notNull(result);
		return result;
	}

	public MessageBox findOutBoxActor(final Actor a) {
		Actor principal;
		MessageBox result;

		principal = this.actorService.findByPrincipal();

		Assert.notNull(principal);
		Assert.notNull(a);
		Assert.isTrue(a.getId() != 0);

		result = this.messageBoxRepository.findOutBoxMessageBoxByActorId(a.getId());
		Assert.notNull(result);
		return result;
	}

	public MessageBox findNotificationBoxActor(final Actor a) {
		Actor principal;
		MessageBox result;
		principal = this.actorService.findByPrincipal();

		Assert.notNull(principal);
		Assert.notNull(a);
		Assert.isTrue(a.getId() != 0);

		result = this.messageBoxRepository.findNotificationMessageBoxByActorId(a.getId());
		Assert.notNull(result);
		return result;
	}

	public void flush() {
		this.messageBoxRepository.flush();
	}

	public Collection<MessageBox> findAllByActorId(final int actorId) {
		Collection<MessageBox> result;
		result = new ArrayList<MessageBox>();
		result = this.messageBoxRepository.findAllByActorId(actorId);
		return result;
	}

	public void deleteInBach(final Collection<MessageBox> boxes) {
		this.messageBoxRepository.deleteInBatch(boxes);

	}
}
