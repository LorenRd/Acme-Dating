package services;

import java.util.ArrayList;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import repositories.AdministratorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Administrator;
import domain.Book;
import domain.Challenge;
import domain.Couple;
import domain.CreditCard;
import domain.Trophy;
import domain.User;

@Service
@Transactional
public class AdministratorService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private AdministratorRepository administratorRepository;

	// Supporting services-------------------------------------------
	@Autowired
	private ActorService actorService;

	@Autowired
	private CreditCardService creditCardService;

	@Autowired
	private TrophyService trophyService;

	@Autowired
	private CoupleService coupleService;

	@Autowired
	private BookService bookService;

	@Autowired
	private ChallengeService challengeService;
	
	@Autowired
	private UserService userService;

	public Administrator findByPrincipal() {
		Administrator res;
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		res = this.administratorRepository.findByUserAccountId(userAccount
				.getId());
		Assert.notNull(res);
		return res;
	}

	public boolean exists(final Integer arg0) {
		return this.administratorRepository.exists(arg0);
	}

	public Administrator create() {
		Administrator principal;
		principal = this.findByPrincipal();
		Assert.notNull(principal);

		Administrator result;
		UserAccount userAccount;
		Authority authority;
		CreditCard creditCard;

		result = new Administrator();
		userAccount = new UserAccount();
		authority = new Authority();
		creditCard = this.creditCardService.create();

		authority.setAuthority("ADMIN");
		userAccount.addAuthority(authority);
		result.setUserAccount(userAccount);
		result.setCreditCard(creditCard);

		return result;

	}

	public Administrator save(final Administrator administrator) {
		Administrator saved;
		UserAccount logedUserAccount;

		final Md5PasswordEncoder passwordEncoder = new Md5PasswordEncoder();
		logedUserAccount = this.actorService.createUserAccount(Authority.ADMIN);
		Assert.notNull(administrator, "administrator.not.null");

		if (administrator.getId() == 0) {
			CreditCard creditCard;
			administrator.getUserAccount().setPassword(
					passwordEncoder.encodePassword(administrator
							.getUserAccount().getPassword(), null));
			creditCard = this.creditCardService.saveNew(administrator
					.getCreditCard());
			administrator.setCreditCard(creditCard);
			saved = this.administratorRepository.saveAndFlush(administrator);

		} else {
			logedUserAccount = LoginService.getPrincipal();
			Assert.notNull(logedUserAccount, "administrator.notLogged");
			Assert.isTrue(
					logedUserAccount.equals(administrator.getUserAccount()),
					"administrator.notEqual.userAccount");
			saved = this.administratorRepository.findOne(administrator.getId());
			Assert.notNull(saved, "administrator.not.null");
			Assert.isTrue(saved.getUserAccount().getUsername()
					.equals(administrator.getUserAccount().getUsername()));
			Assert.isTrue(saved.getUserAccount().getPassword()
					.equals(administrator.getUserAccount().getPassword()));
			saved = this.administratorRepository.saveAndFlush(administrator);
		}

		return saved;
	}

	public Collection<Administrator> findAll() {
		Collection<Administrator> result;

		result = this.administratorRepository.findAll();
		Assert.notNull(result);
		return result;
	}

	public Administrator findOne(final int adminId) {
		Administrator result;

		result = this.administratorRepository.findOne(adminId);
		Assert.notNull(result);
		return result;

	}

	public void flush() {
		this.administratorRepository.flush();
	}

	// public void computeTrophies() {
	// Collection<Trophy> trophies;
	// Collection<Couple> couples;
	// Collection<Challenge> challenges;
	// Collection<Experience> experiences;
	// Collection<User> users;
	//
	// Collection<Trophy> reachedTrophies;
	//
	// trophies = this.trophyService.findAll();
	// couples = this.coupleService.findAll();
	// challenges = this.challengeService.findAll();
	// experiences = this.experienceService.findAll();
	// users = this.userService.findAll();
	// //.equals("COMPLETED")
	// for(Trophy t : trophies){
	// for(Couple c : couples){
	// for(Challenge ch : challenges){
	// if(((c.getScore() >= t.getScoreToReach()) || () )){
	//
	// }
	// }
	// }
	// }
	// }

	public void computeTrophies(){
		Collection<Trophy> trophies = this.trophyService.findAll();
		Collection<Couple> couples = this.coupleService.findAll();
		
		for(Couple c : couples){
			Collection<Trophy> reachedTrophies = new ArrayList<Trophy>();
			Collection<Book> books = this.bookService.findAllByCoupleId(c.getId());
			Collection<User> users = this.userService.findByCoupleId(c.getId());
			Integer cont = 0;
			Integer numberOfCompletedChallenges;
			for(User u : users){
				Collection<Challenge> challenges = this.challengeService.findAllCompletedBySenderId(u.getId());
				numberOfCompletedChallenges = challenges.size();
				cont = numberOfCompletedChallenges;
				break;
			}
			for(Trophy t : trophies){
				if((c.getScore() >= t.getScoreToReach()) || (books.size() >= t.getExperiencesToShare()) || (cont >= t.getChallengesToComplete()) ){
					reachedTrophies.add(t);
				}
			}
			c.setTrophies(reachedTrophies);
		}
	}
}
