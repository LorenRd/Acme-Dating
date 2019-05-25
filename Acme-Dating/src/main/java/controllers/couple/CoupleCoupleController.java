package controllers.couple;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import domain.Couple;
import domain.Trophy;
import domain.User;

import services.CoupleService;
import services.UserService;

@Controller
@RequestMapping("/couple")
public class CoupleCoupleController {

	//Services
	@Autowired
	private CoupleService		coupleService;
	
	@Autowired
	private UserService			userService;

	
	//Display
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView showCouplePanel() {
		final ModelAndView result;
		Couple couple;
		User principal;
		Collection<Trophy> trophies;
		User darling;
		boolean hasCouple = false;
		
		result = new ModelAndView("couple/couplePanel");
		principal = this.userService.findByPrincipal();

		if(principal.getCouple() != null){
			principal = this.userService.findByPrincipal();
			couple = this.coupleService.findByUser();
			darling = this.userService.findDarling(couple.getId());
			
			trophies = couple.getTrophies();
			hasCouple = true;
			
			result.addObject("couple", couple);
			result.addObject("darling", darling);		
			result.addObject("trophies", trophies);	

		}
		
		result.addObject("principal", principal);
		result.addObject("hasCouple", hasCouple);	

		return result;
	}
}
