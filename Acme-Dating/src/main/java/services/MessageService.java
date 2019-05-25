
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.MessageRepository;
import domain.Actor;
import domain.Message;
import domain.MessageBox;

@Service
@Transactional
public class MessageService {

	// Managed Repository

	@Autowired
	private MessageRepository	messageRepository;

	// Supporting services

	@Autowired
	private MessageBoxService	messageBoxService;

	@Autowired
	private ActorService		actorService;


	// Simple CRUD methods

	public Message create() {
		Message result;
		Actor principal;
		MessageBox box;
		List<MessageBox> boxes;
		Collection<Actor> recipients;

		principal = this.actorService.findByPrincipal();
		Assert.notNull(principal);

		result = new Message();
		result.setSender(principal);
		boxes = new ArrayList<MessageBox>();
		box = this.messageBoxService.findOutBoxActor(principal);
		boxes.add(box);
		result.setMessageBoxes(boxes);
		recipients = new ArrayList<Actor>();
		result.setRecipients(recipients);
		result.setMoment(new Date(System.currentTimeMillis() - 1));

		return result;
	}

	// An actor who is authenticated must be able to exchange messages with
	// other actors

	public Message save(final Message message) {
		Message result;
		Message saved;
		Actor principal;
		Date moment;
		List<MessageBox> boxes;
		Collection<Actor> recipients;
		MessageBox box1;
		Collection<Message> messages;
		MessageBox outBox;

		Assert.notNull(message);

		principal = this.actorService.findByPrincipal();

		Assert.notNull(principal);

		moment = new Date(System.currentTimeMillis() - 1);

		message.setMoment(moment);
		message.setSender(principal);

		recipients = message.getRecipients();
		boxes = message.getMessageBoxes();
		Assert.notNull(boxes);
		outBox = message.getMessageBoxes().iterator().next();

		saved = this.messageRepository.save(message);
		Assert.notNull(saved);

		// De no ser así, llega a las inBoxes de los recipients
		for (final Actor a : recipients) {
			box1 = this.messageBoxService.findInBoxActor(a);
			boxes.add(box1);
			messages = box1.getMessages();
			messages.add(saved);
			box1.setMessages(messages);
			this.messageBoxService.save2(box1, a);
		}

		saved.setMessageBoxes(boxes);

		result = this.messageRepository.save(saved);
		Assert.notNull(result);

		Collection<Message> ms;
		ms = outBox.getMessages();
		ms.add(result);
		outBox.setMessages(ms);
		this.messageBoxService.save2(outBox, principal);

		return result;
	}

	public Message findOne(final int messageId) {
		Message result;

		result = this.messageRepository.findOne(messageId);
		Assert.notNull(result);

		return result;
	}

	public Collection<Message> findAll() {
		Collection<Message> result;

		result = this.messageRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	// Other business methods

	public Message broadcast(final Message message) {
		Message result;
		Message saved;
		Actor principal;
		Date moment;
		List<MessageBox> boxes;
		Collection<Actor> recipients;
		MessageBox box1;
		Collection<Message> messages;
		MessageBox outBox;

		Assert.notNull(message);

		principal = this.actorService.findByPrincipal();

		Assert.notNull(principal);

		moment = new Date(System.currentTimeMillis() - 1);

		message.setMoment(moment);
		message.setSender(principal);

		Assert.isTrue(!message.getRecipients().contains(principal));

		recipients = this.actorService.findAll();
		boxes = message.getMessageBoxes();
		Assert.notNull(boxes);
		outBox = message.getMessageBoxes().iterator().next();

		saved = this.messageRepository.save(message);
		Assert.notNull(saved);

		for (final Actor a : recipients) {
			box1 = this.messageBoxService.findNotificationBoxActor(a);
			boxes.add(box1);
			messages = box1.getMessages();
			messages.add(saved);
			box1.setMessages(messages);
			this.messageBoxService.save2(box1, a);
		}

		saved.setMessageBoxes(boxes);

		result = this.messageRepository.save(saved);
		Assert.notNull(result);

		Collection<Message> ms;
		ms = outBox.getMessages();
		ms.add(result);
		outBox.setMessages(ms);
		this.messageBoxService.save2(outBox, principal);

		return result;
	}

	public void flush() {
		this.messageRepository.flush();
	}
}
