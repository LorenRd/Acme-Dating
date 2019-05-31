package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import domain.Couple;
import domain.Task;
import domain.User;

import repositories.TaskRepository;
import repositories.UserRepository;
import security.LoginService;
import security.UserAccount;

@Service
@Transactional
public class TaskService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private TaskRepository taskRepository;

	@Autowired
	private UserRepository userRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private CoupleService coupleService;

	@Autowired
	private Validator validator;

	// Simple CRUD Methods

	public Task create() {
		Task result;
		final Couple principal;

		principal = this.coupleService.findByUser();
		Assert.notNull(principal);

		result = new Task();
		result.setCouple(principal);
		return result;
	}

	public Task save(final Task task) {
		Task result;

		Assert.notNull(task);
		Assert.isTrue(this.owner(task));

		result = this.taskRepository.save(task);
		Assert.notNull(result);

		return result;
	}

	public Task findOne(final int taskId) {
		Task result;

		result = this.taskRepository.findOne(taskId);
		Assert.notNull(result);
		return result;
	}

	public void delete(final Task task) {
		Couple principal;

		Assert.notNull(task);

		principal = this.coupleService.findByUser();
		Assert.notNull(principal);

		Assert.isTrue(task.getCouple().getId() == principal.getId());

		this.taskRepository.delete(task);
	}

	// Additional functions

	public Collection<Task> findByCoupleId(final int coupleId) {
		Collection<Task> result;

		result = this.taskRepository.findByCoupleId(coupleId);
		return result;
	}

	public Task reconstruct(final Task task, final BindingResult binding) {
		Task result;
		if (task.getId() == 0) {
			result = task;
			result.setCouple(this.coupleService.findByUser());
		} else {
			result = this.taskRepository.findOne(task.getId());
		}
		result.setTitle(task.getTitle());
		result.setIsCompleted(task.getIsCompleted());

		this.validator.validate(result, binding);

		return result;
	}

	public void changeStatus(Task task) {
		if (task.getIsCompleted()) {
			task.setIsCompleted(false);
		} else {
			task.setIsCompleted(true);
		}
	}

	public boolean owner(Task task) {
		boolean result = true;

		UserAccount userAccountPrincipal = LoginService.getPrincipal();
		User principal = this.userRepository
				.findByUserAccountId(userAccountPrincipal.getId());

		if (task.getCouple() != principal.getCouple()) {
			result = false;
		}
		return result;
	}

}
