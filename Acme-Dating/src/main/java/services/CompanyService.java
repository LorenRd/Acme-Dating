package services;

import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import repositories.CompanyRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Company;
import domain.CreditCard;

@Service
@Transactional
public class CompanyService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private CompanyRepository companyRepository;

	// Supporting services ----------------------------------------------------
	@Autowired
	private ActorService actorService;

	@Autowired
	private CreditCardService creditCardService;

	 

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

		return result;
	}

	public Company save(final Company company) {
		Company saved;
		UserAccount logedUserAccount;
		Md5PasswordEncoder encoder;

		encoder = new Md5PasswordEncoder();
		logedUserAccount = this.actorService
				.createUserAccount(Authority.COMPANY);
		Assert.notNull(company, "company.not.null");

		if (this.exists(company.getId())) {
			logedUserAccount = LoginService.getPrincipal();
			Assert.notNull(logedUserAccount, "company.notLogged");
			Assert.isTrue(logedUserAccount.equals(company.getUserAccount()),
					"company.notEqual.userAccount");
			saved = this.companyRepository.findOne(company.getId());
			Assert.notNull(saved, "company.not.null");
			Assert.isTrue(
					saved.getUserAccount().getUsername()
							.equals(company.getUserAccount().getUsername()),
					"company.notEqual.username");
			Assert.isTrue(
					saved.getUserAccount().getPassword()
							.equals(company.getUserAccount().getPassword()),
					"company.notEqual.password");

			saved = this.companyRepository.save(company);

		} else {
			CreditCard creditCard;
			company.getUserAccount().setPassword(
					encoder.encodePassword(company.getUserAccount()
							.getPassword(), null));
			creditCard = this.creditCardService
					.saveNew(company.getCreditCard());
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
		result = this.companyRepository
				.findByUserAccountId(userAccount.getId());
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
}
