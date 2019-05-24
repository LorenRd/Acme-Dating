package services;

import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import repositories.UserRepository;
import security.LoginService;
import security.UserAccount;
import domain.User;

@Service
@Transactional
public class UserService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private UserRepository userRepository;

	// Supporting services ----------------------------------------------------

	// Additional functions

	// Simple CRUD Methods

	public User findOne(final int userId) {
		User result;

		result = this.userRepository.findOne(userId);
		Assert.notNull(result);
		return result;

	}

	public Collection<User> findAll() {
		Collection<User> result;

		result = this.userRepository.findAll();
		Assert.notNull(result);
		return result;
	}

	public User findByPrincipal() {
		User result;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		result = this.userRepository.findByUserAccountId(userAccount.getId());
		Assert.notNull(result);

		return result;

	}

	public User findByUserAccount(final UserAccount userAccount) {
		Assert.notNull(userAccount);
		User result;
		result = this.userRepository.findByUserAccountId(userAccount.getId());
		return result;
	}

	public boolean exists(final Integer arg0) {
		return this.userRepository.exists(arg0);
	}

}
