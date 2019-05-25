
package services;

import java.util.Collection;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.CustomisationRepository;
import repositories.UserRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import security.UserAccountRepository;
import domain.CreditCard;
import domain.User;
import forms.UserForm;

@Service
@Transactional
public class UserService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private UserRepository	userRepository;

	@Autowired
	private UserAccountRepository	useraccountRepository;

	@Autowired
	private CustomisationRepository	customisationRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private CoupleService coupleService;
	@Autowired
	private ActorService			actorService;

	@Autowired
	private CreditCardService		creditCardService;

	@Autowired
	private Validator				validator;


	// Additional functions

	// Simple CRUD Methods

	public User save(final User user) {
		User result;

		result = this.userRepository.save(user);

		return result;
	}

		result = new User();
		creditCard = new CreditCard();

		//Nuevo userAccount con user en la lista de authorities
		final UserAccount userAccount = this.actorService.createUserAccount(Authority.USER);

		result.setUserAccount(userAccount);
		result.setCreditCard(creditCard);

		return result;
	}

	public User save(final User user) {
		User saved;
		UserAccount logedUserAccount;

		final Md5PasswordEncoder passwordEncoder = new Md5PasswordEncoder();
		logedUserAccount = this.actorService.createUserAccount(Authority.USER);
		Assert.notNull(user, "user.not.null");

		if (user.getId() == 0) {
			CreditCard creditCard;
			user.getUserAccount().setPassword(passwordEncoder.encodePassword(user.getUserAccount().getPassword(), null));
			creditCard = this.creditCardService.saveNew(user.getCreditCard());
			user.setCreditCard(creditCard);
			saved = this.userRepository.saveAndFlush(user);
		} else {
			logedUserAccount = LoginService.getPrincipal();
			Assert.notNull(logedUserAccount, "provider.notLogged");
			Assert.isTrue(logedUserAccount.equals(user.getUserAccount()), "user.notEqual.userAccount");
			saved = this.userRepository.findOne(user.getId());
			Assert.notNull(saved, "user.not.null");
			Assert.isTrue(saved.getUserAccount().getUsername().equals(user.getUserAccount().getUsername()));
			Assert.isTrue(saved.getUserAccount().getPassword().equals(user.getUserAccount().getPassword()));
			saved = this.userRepository.saveAndFlush(user);
		}

		return saved;
	}

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

	public Collection<User> findByCoupleId(final int coupleId) {
		Collection<User> result;
		result = this.userRepository.findByCoupleId(coupleId);
		return result;
	}

	public boolean exists(final Integer arg0) {
		return this.userRepository.exists(arg0);
	}

	public User findDarling(final int coupleId) {
		User darling = null;
		User principal;
		Collection <User> users;
		
		principal = this.findByPrincipal();
		
		users = this.coupleService.findUsersOfACouple(coupleId);
		
		for (User u : users) {
			if(u.getId() != principal.getId())
				darling = u;
		}
		
		return darling;
	}
	public UserForm construct(final User user) {
		final UserForm userForm = new UserForm();
		userForm.setBrandName(user.getCreditCard().getBrandName());
		userForm.setCheckBox(userForm.getCheckBox());
		userForm.setCVV(user.getCreditCard().getCVV());
		userForm.setEmail(user.getEmail());
		userForm.setExpirationMonth(user.getCreditCard().getExpirationMonth());
		userForm.setExpirationYear(user.getCreditCard().getExpirationYear());
		userForm.setHolderName(user.getCreditCard().getHolderName());
		userForm.setId(user.getId());
		userForm.setName(user.getName());
		userForm.setNumber(user.getCreditCard().getNumber());
		userForm.setPhone(user.getPhone());
		userForm.setPhoto(user.getPhoto());
		userForm.setSurname(user.getSurname());
		userForm.setUsername(user.getUserAccount().getUsername());
		return userForm;
	}

	public User reconstruct(final UserForm userForm, final BindingResult binding) {
		User result;

		result = this.create();
		result.getUserAccount().setUsername(userForm.getUsername());
		result.getUserAccount().setPassword(userForm.getPassword());
		result.setEmail(userForm.getEmail());
		result.setName(userForm.getName());
		result.setPhoto(userForm.getPhoto());
		result.setSurname(userForm.getSurname());
		result.getCreditCard().setBrandName(userForm.getBrandName());
		result.getCreditCard().setCVV(userForm.getCVV());
		result.getCreditCard().setExpirationMonth(userForm.getExpirationMonth());
		result.getCreditCard().setExpirationYear(userForm.getExpirationYear());
		result.getCreditCard().setHolderName(userForm.getHolderName());
		result.getCreditCard().setNumber(userForm.getNumber());

		if (!StringUtils.isEmpty(userForm.getPhone())) {
			final Pattern pattern = Pattern.compile("^\\d{4,}$", Pattern.CASE_INSENSITIVE);
			final Matcher matcher = pattern.matcher(userForm.getPhone());
			if (matcher.matches())
				userForm.setPhone(this.customisationRepository.findAll().iterator().next().getCountryCode() + userForm.getPhone());
		}
		result.setPhone(userForm.getPhone());

		if (!userForm.getPassword().equals(userForm.getPasswordChecker()))
			binding.rejectValue("passwordChecker", "user.validation.passwordsNotMatch", "Passwords doesnt match");
		if (!this.useraccountRepository.findUserAccountsByUsername(userForm.getUsername()).isEmpty())
			binding.rejectValue("username", "user.validation.usernameExists", "This username already exists");
		if (userForm.getCheckBox() == false)
			binding.rejectValue("checkBox", "user.validation.checkBox", "This checkbox must be checked");

		this.validator.validate(result, binding);
		this.userRepository.flush();

		return result;

	}

	public User reconstructPruned(final User user, final BindingResult binding) {
		User result;
		if (user.getId() == 0)
			result = user;
		else
			result = this.userRepository.findOne(user.getId());
		result.setEmail(user.getEmail());
		result.setName(user.getName());
		result.setPhoto(user.getPhoto());
		result.setSurname(user.getSurname());

		if (!StringUtils.isEmpty(user.getPhone())) {
			final Pattern pattern = Pattern.compile("^\\d{4,}$", Pattern.CASE_INSENSITIVE);
			final Matcher matcher = pattern.matcher(user.getPhone());
			if (matcher.matches())
				user.setPhone(this.customisationRepository.findAll().iterator().next().getCountryCode() + user.getPhone());
		}
		result.setPhone(user.getPhone());

		this.validator.validate(result, binding);
		this.userRepository.flush();
		return result;
	}

}
