
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.CoupleRepository;
import domain.Book;
import domain.Challenge;
import domain.Couple;
import domain.Record;
import domain.Task;
import domain.Trophy;
import domain.User;

@Service
@Transactional
public class CoupleService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private CoupleRepository	coupleRepository;

	// Supporting services ----------------------------------------------------
	@Autowired
	private UserService			userService;

	@Autowired
	private RecordService		recordService;

	@Autowired
	private TaskService			taskService;

	@Autowired
	private BookService			bookService;

	@Autowired
	private ChallengeService	challengeService;


	public Couple create(final User sender, final User recipient) {
		Couple result;
		Date startDate;

		Assert.notNull(sender);
		Assert.notNull(recipient);

		result = new Couple();
		startDate = new Date(System.currentTimeMillis() - 1);

		result.setScore(0);
		result.setStartDate(startDate);
		result.setTrophies(new ArrayList<Trophy>());

		sender.setCouple(result);
		recipient.setCouple(result);

		return result;
	}

	public Couple save(final Couple couple, final User sender, final User recipient) {
		Couple result;
		Date startDate;

		Assert.notNull(couple);

		startDate = new Date(System.currentTimeMillis() - 1);
		couple.setStartDate(startDate);

		result = this.coupleRepository.save(couple);

		sender.setCouple(result);
		recipient.setCouple(result);

		this.userService.save(sender);
		this.userService.save(recipient);

		Assert.notNull(result);

		return result;
	}

	public Couple findByUser() {
		User principal;

		principal = this.userService.findByPrincipal();
		Assert.notNull(principal);

		return principal.getCouple();
	}

	public Collection<User> findUsersOfACouple(final int coupleId) {
		Collection<User> users;

		users = this.coupleRepository.findUsersOfACouple(coupleId);

		return users;
	}

	public void delete(final Couple couple) {
		User principal;
		Collection<Record> records;
		Collection<Task> tasks;
		Collection<Book> books;
		Collection<Challenge> challenges;
		Collection<User> users;

		Assert.notNull(couple);
		Assert.isTrue(couple.getId() != 0);

		principal = this.userService.findByPrincipal();
		Assert.notNull(principal);

		records = this.recordService.findByCoupleId(couple.getId());
		for (final Record r : records)
			this.recordService.delete(r);

		tasks = this.taskService.findByCoupleId(couple.getId());
		for (final Task t : tasks)
			this.taskService.delete(t);

		books = this.bookService.findAllByCoupleId(couple.getId());
		for (final Book b : books)
			this.bookService.delete(b);

		challenges = this.challengeService.findByRecipientId(principal.getId());
		challenges.addAll(this.challengeService.findBySenderId(principal.getId()));
		for (final Challenge c : challenges)
			this.challengeService.delete(c);
		
		couple.getTrophies().clear();
		
		users = this.findUsersOfACouple(couple.getId());
		for (User u : users) {
			u.setCouple(null);
		}

		this.coupleRepository.delete(couple);
		this.flush();
	}

	public Couple findOne(final int coupleId) {
		Couple result;

		result = this.coupleRepository.findOne(coupleId);
		Assert.notNull(result);
		return result;
	}

	public Collection<Couple> findAll() {
		Collection<Couple> result;

		result = this.coupleRepository.findAll();
		Assert.notNull(result);
		return result;
	}

	public void flush() {
		this.coupleRepository.flush();
	}
}
