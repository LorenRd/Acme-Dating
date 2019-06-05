package controllers.administrator;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.AdministratorService;
import services.BookService;
import services.CategoryService;
import services.ChallengeService;
import services.ExperienceService;

import domain.Category;

@Controller
@RequestMapping("/dashboard/administrator")
public class DashboardAdministratorController {

	// Services

	@Autowired
	private ExperienceService experienceService;

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private ChallengeService challengeService;

	@Autowired
	private AdministratorService administratorService;

	@Autowired
	private BookService bookService;

	// Display

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display() {
		final ModelAndView result;
		final Double avgExperiencesPerCouple, minExperiencesPerCouple, maxExperiencesPerCouple, stddevExperiencesPerCouple;
		final Double avgExperiencesPerCompany, minExperiencesPerCompany, maxExperiencesPerCompany, stddevExperiencesPerCompany, avgPriceOfExperiencesPerCompany;

		final Collection<Category> mostUsedCategory;
		final Collection<Category> leastUsedCategory;

		final Double avgCompletedChallengesPerSender, minCompletedChallengesPerSender, maxCompletedChallengesPerSender, stddevCompletedChallengesPerSender;

		// Stadistics

		// avg
		avgExperiencesPerCouple = this.bookService.avgExperiencesPerCouple();

		// min
		minExperiencesPerCouple = this.bookService.minExperiencesPerCouple();

		// max
		maxExperiencesPerCouple = this.bookService.maxExperiencesPerCouple();

		// standard Deviation
		stddevExperiencesPerCouple = this.bookService
				.stddevExperiencesPerCouple();

		// avg
		avgExperiencesPerCompany = this.experienceService
				.avgExperiencesPerCompany();

		// min
		minExperiencesPerCompany = this.experienceService
				.minExperiencesPerCompany();

		// max
		maxExperiencesPerCompany = this.experienceService
				.maxExperiencesPerCompany();

		// standard Deviation
		stddevExperiencesPerCompany = this.experienceService
				.stddevExperiencesPerCompany();

		avgPriceOfExperiencesPerCompany = this.experienceService
				.avgPriceOfExperiencesPerCompany();

		mostUsedCategory = this.categoryService.mostUsedCategory();
		leastUsedCategory = this.categoryService.leastUsedCategory();

		// Stadistics

		// avg
		avgCompletedChallengesPerSender = this.challengeService
				.avgCompletedChallengesPerSender();

		// min
		minCompletedChallengesPerSender = this.challengeService
				.minCompletedChallengesPerSender();

		// max
		maxCompletedChallengesPerSender = this.challengeService
				.maxCompletedChallengesPerSender();

		// standard Deviation
		stddevCompletedChallengesPerSender = this.challengeService
				.stddevCompletedChallengesPerSender();

		result = new ModelAndView("administrator/dashboard");
		result.addObject("avgExperiencesPerCouple", avgExperiencesPerCouple);
		result.addObject("minExperiencesPerCouple", minExperiencesPerCouple);
		result.addObject("maxExperiencesPerCouple", maxExperiencesPerCouple);
		result.addObject("stddevExperiencesPerCouple",
				stddevExperiencesPerCouple);

		result.addObject("avgExperiencesPerCompany", avgExperiencesPerCompany);
		result.addObject("minExperiencesPerCompany", minExperiencesPerCompany);
		result.addObject("maxExperiencesPerCompany", maxExperiencesPerCompany);
		result.addObject("stddevExperiencesPerCompany",
				stddevExperiencesPerCompany);
		result.addObject("avgPriceOfExperiencesPerCompany",
				avgPriceOfExperiencesPerCompany);

		result.addObject("mostUsedCategory", mostUsedCategory);
		result.addObject("leastUsedCategory", leastUsedCategory);

		result.addObject("avgCompletedChallengesPerSender",
				avgCompletedChallengesPerSender);
		result.addObject("minCompletedChallengesPerSender",
				minCompletedChallengesPerSender);
		result.addObject("maxCompletedChallengesPerSender",
				maxCompletedChallengesPerSender);
		result.addObject("stddevCompletedChallengesPerSender",
				stddevCompletedChallengesPerSender);

		return result;

	}

	@RequestMapping(value = "/display", method = RequestMethod.GET, params = "computeTrophies")
	public ModelAndView computeTrophies() {
		final ModelAndView result;

		this.administratorService.computeTrophies();

		result = new ModelAndView("redirect:display.do");

		return result;
	}

}
