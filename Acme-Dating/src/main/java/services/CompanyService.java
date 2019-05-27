
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
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

import repositories.CompanyRepository;
import repositories.CustomisationRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import security.UserAccountRepository;
import domain.Book;
import domain.Company;
import domain.CreditCard;
import domain.Experience;
import domain.Message;
import domain.MessageBox;
import forms.CompanyForm;

@Service
@Transactional
public class CompanyService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private CompanyRepository			companyRepository;

	@Autowired
	private CustomisationRepository		customisationRepository;

	@Autowired
	private UserAccountRepository		useraccountRepository;

	// Supporting services ----------------------------------------------------
	@Autowired
	private ActorService				actorService;

	@Autowired
	private CreditCardService			creditCardService;

	@Autowired
	private ExperienceService			experienceService;

	@Autowired
	private FeatureService				featureService;

	@Autowired
	private MessageService				messageService;

	@Autowired
	private ExperienceCommentService	experienceCommentService;

	@Autowired
	private BookService					bookService;

	@Autowired
	private MessageBoxService			messageBoxService;

	@Autowired
	private Validator					validator;


	// Simple CRUD Methods

	// Simple CRUD Methods

	public boolean exists(final Integer arg0) {
		return this.companyRepository.exists(arg0);
	}

	public Company create() {
		Company result;
		UserAccount userAccount;
		Authority authority;
		CreditCard creditCard;

		result = new Company();
		userAccount = new UserAccount();
		authority = new Authority();
		creditCard = new CreditCard();

		authority.setAuthority("COMPANY");
		userAccount.addAuthority(authority);

		Assert.notNull(userAccount);
		Assert.notNull(creditCard);

		result.setUserAccount(userAccount);
		result.setCreditCard(creditCard);
		result.setMessageBoxes(new ArrayList<MessageBox>());

		return result;
	}

	public Company save(final Company company) {
		Company saved;
		UserAccount logedUserAccount;
		Md5PasswordEncoder encoder;
		final List<MessageBox> messageBoxes;

		encoder = new Md5PasswordEncoder();
		logedUserAccount = this.actorService.createUserAccount(Authority.COMPANY);
		Assert.notNull(company, "company.not.null");

		if (this.exists(company.getId())) {
			logedUserAccount = LoginService.getPrincipal();
			Assert.notNull(logedUserAccount, "company.notLogged");
			Assert.isTrue(logedUserAccount.equals(company.getUserAccount()), "company.notEqual.userAccount");
			saved = this.companyRepository.findOne(company.getId());
			Assert.notNull(saved, "company.not.null");
			Assert.isTrue(saved.getUserAccount().getUsername().equals(company.getUserAccount().getUsername()), "company.notEqual.username");
			Assert.isTrue(saved.getUserAccount().getPassword().equals(company.getUserAccount().getPassword()), "company.notEqual.password");

			saved = this.companyRepository.save(company);

		} else {
			CreditCard creditCard;
			company.getUserAccount().setPassword(encoder.encodePassword(company.getUserAccount().getPassword(), null));
			creditCard = this.creditCardService.saveNew(company.getCreditCard());
			company.setCreditCard(creditCard);
			saved = this.companyRepository.saveAndFlush(company);
			messageBoxes = this.messageBoxService.createSystemBoxes(saved);
			saved.setMessageBoxes(messageBoxes);
		}
		return saved;
	}

	public Company findOne(final int companyId) {
		Company result;

		result = this.companyRepository.findOne(companyId);
		Assert.notNull(result);
		return result;

	}

	public Collection<Company> findAll() {
		Collection<Company> result;

		result = this.companyRepository.findAll();
		Assert.notNull(result);
		return result;
	}

	// Other business methods

	public Company findByPrincipal() {
		Company result;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		result = this.companyRepository.findByUserAccountId(userAccount.getId());
		Assert.notNull(result);

		return result;

	}

	public Company findByUserAccountId(final int userAccountId) {
		Assert.notNull(userAccountId);
		Company result;
		result = this.companyRepository.findByUserAccountId(userAccountId);
		return result;
	}

	public void flush() {
		this.companyRepository.flush();
	}

	public void computeScore() {
		Collection<Experience> experiences;

		experiences = this.experienceService.findAll();
		for (final Experience e : experiences) {
			Collection<Book> books = new ArrayList<Book>();
			Double score = 0.0;
			int i = 0;
			books = this.bookService.findAllByCompanyId(e.getCompany().getId());
			for (final Book b : books)
				if (b.getScore() != null) {
					i++;
					score += b.getScore();
				}
			if (i > 0)
				score = score / i;
			e.setScore(score);
		}
	}

	public CompanyForm construct(final Company company) {
		final CompanyForm companyForm = new CompanyForm();
		companyForm.setBrandName(company.getCreditCard().getBrandName());
		companyForm.setCheckBox(companyForm.getCheckBox());
		companyForm.setCommercialName(company.getCommercialName());
		companyForm.setCVV(company.getCreditCard().getCVV());
		companyForm.setEmail(company.getEmail());
		companyForm.setExpirationMonth(company.getCreditCard().getExpirationMonth());
		companyForm.setExpirationYear(company.getCreditCard().getExpirationYear());
		companyForm.setHolderName(company.getCreditCard().getHolderName());
		companyForm.setId(company.getId());
		companyForm.setName(company.getName());
		companyForm.setNumber(company.getCreditCard().getNumber());
		companyForm.setPhone(company.getPhone());
		companyForm.setPhoto(company.getPhoto());
		companyForm.setSurname(company.getSurname());
		companyForm.setUsername(company.getUserAccount().getUsername());
		return companyForm;
	}

	public Company reconstruct(final CompanyForm companyForm, final BindingResult binding) {
		Company result;

		result = this.create();
		result.getUserAccount().setUsername(companyForm.getUsername());
		result.getUserAccount().setPassword(companyForm.getPassword());
		result.setCommercialName(companyForm.getCommercialName());
		result.setEmail(companyForm.getEmail());
		result.setName(companyForm.getName());
		result.setPhoto(companyForm.getPhoto());
		result.setSurname(companyForm.getSurname());
		result.getCreditCard().setBrandName(companyForm.getBrandName());
		result.getCreditCard().setCVV(companyForm.getCVV());
		result.getCreditCard().setExpirationMonth(companyForm.getExpirationMonth());
		result.getCreditCard().setExpirationYear(companyForm.getExpirationYear());
		result.getCreditCard().setHolderName(companyForm.getHolderName());
		result.getCreditCard().setNumber(companyForm.getNumber());

		if (!StringUtils.isEmpty(companyForm.getPhone())) {
			final Pattern pattern = Pattern.compile("^\\d{4,}$", Pattern.CASE_INSENSITIVE);
			final Matcher matcher = pattern.matcher(companyForm.getPhone());
			if (matcher.matches())
				companyForm.setPhone(this.customisationRepository.findAll().iterator().next().getCountryCode() + companyForm.getPhone());
		}
		result.setPhone(companyForm.getPhone());

		if (!companyForm.getPassword().equals(companyForm.getPasswordChecker()))
			binding.rejectValue("passwordChecker", "company.validation.passwordsNotMatch", "Passwords doesnt match");
		if (!this.useraccountRepository.findUserAccountsByUsername(companyForm.getUsername()).isEmpty())
			binding.rejectValue("username", "company.validation.usernameExists", "This username already exists");
		if (companyForm.getCheckBox() == false)
			binding.rejectValue("checkBox", "company.validation.checkBox", "This checkbox must be checked");

		this.validator.validate(result, binding);
		this.companyRepository.flush();

		return result;
	}

	public Company reconstructPruned(final Company company, final BindingResult binding) {
		Company result;
		if (company.getId() == 0)
			result = company;
		else
			result = this.companyRepository.findOne(company.getId());
		result.setCommercialName(company.getCommercialName());
		result.setEmail(company.getEmail());
		result.setName(company.getName());
		result.setPhoto(company.getPhoto());
		result.setSurname(company.getSurname());

		if (!StringUtils.isEmpty(company.getPhone())) {
			final Pattern pattern = Pattern.compile("^\\d{4,}$", Pattern.CASE_INSENSITIVE);
			final Matcher matcher = pattern.matcher(company.getPhone());
			if (matcher.matches())
				company.setPhone(this.customisationRepository.findAll().iterator().next().getCountryCode() + company.getPhone());
		}
		result.setPhone(company.getPhone());
		this.validator.validate(result, binding);
		this.companyRepository.flush();
		return result;
	}

	public void delete() {
		/*
		 * Orden de borrado:
		 * 1 Comments de la experience
		 * 2 Experience
		 * 3 Mensajes
		 * 4 CC
		 * 5 Company
		 */
		Company principal;
		Collection<Experience> experiences;
		final Collection<Message> messagesR;
		final Collection<Message> messagesS;

		principal = this.findByPrincipal();
		Assert.notNull(principal);

		experiences = this.experienceService.findByCompany(principal.getId());
		for (final Experience e : experiences)
			this.experienceService.delete(e);

		messagesR = this.messageService.findByRecipientId(principal.getId());
		for (final Message m : messagesR)
			this.messageService.deleteRecipient(m);

		messagesS = this.messageService.findBySenderId(principal.getId());
		for (final Message m : messagesS)
			this.messageService.deleteSender(m);

		this.companyRepository.delete(principal);

		this.creditCardService.delete(principal.getCreditCard());

	}

}
