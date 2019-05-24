
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.CompanyRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Book;
import domain.Company;
import domain.CreditCard;
import domain.Experience;
import domain.MessageBox;

@Service
@Transactional
public class CompanyService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private CompanyRepository	companyRepository;

	// Supporting services ----------------------------------------------------
	@Autowired
	private ActorService		actorService;

	@Autowired
	private CreditCardService	creditCardService;

	@Autowired
	private ExperienceService experienceService;
	 
	@Autowired
	private BookService bookService;
	 // Simple CRUD Methods
	@Autowired
	private MessageBoxService	messageBoxService;


	// Simple CRUD Methods

	public boolean exists(final Integer arg0) {
		return this.companyRepository.exists(arg0);
	}

	public Company create() {
		Company result;
		UserAccount userAccount;
		Authority authority;
		CreditCard creditCard;
		List<MessageBox> boxes;

		result = new Company();
		userAccount = new UserAccount();
		authority = new Authority();
		creditCard = new CreditCard();
		boxes = this.messageBoxService.createSystemBoxes(result);

		authority.setAuthority("COMPANY");
		userAccount.addAuthority(authority);

		Assert.notNull(userAccount);
		Assert.notNull(creditCard);

		result.setUserAccount(userAccount);
		result.setCreditCard(creditCard);
		result.setMessageBoxes(boxes);

		return result;
	}

	public Company save(final Company company) {
		Company saved;
		UserAccount logedUserAccount;
		Md5PasswordEncoder encoder;

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

	public void computeScore(){
		Collection<Experience> experiences;
		
		experiences = this.experienceService.findAll();
		for (Experience e : experiences) {
			Collection<Book> books = new ArrayList<Book>();
			Double score = 0.0;
			int i = 0;
			books = this.bookService.findAllByCompanyId(e.getCompany().getId());
			for (Book b : books) {
				if (b.getScore() != null)
				{
					i++;
					score += b.getScore();
				}
			}
			if(i>0){
				score = score/i;
			}
			e.setScore(score);
		}
	}
}
