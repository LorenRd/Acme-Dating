package controllers.any;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ExperienceCommentService;
import controllers.AbstractController;
import domain.ExperienceComment;

@Controller
@RequestMapping("/experienceComment")
public class ExperienceCommentController extends AbstractController {
	
	// Services

	@Autowired
	private ExperienceCommentService			experienceCommentService;
	
	//Create comentario raiz

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int experienceId) {
		ModelAndView result;
		ExperienceComment experienceComment;
		
		experienceComment = this.experienceCommentService.create(true, experienceId);
		
		result = this.createModelAndView(experienceComment);

		return result;
	}

	//Create con comentario padre
	@RequestMapping(value = "/createReply", method = RequestMethod.GET)
	public ModelAndView createReply(@RequestParam final int experienceCommentId) {
		ModelAndView result;
		ExperienceComment experienceComment;
		
		experienceComment = this.experienceCommentService.create(false, experienceCommentId);

		result = this.createModelAndView(experienceComment);

		return result;
	}
	
	
	// SAVE DE CREATE
	
	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView createFinal(@ModelAttribute("experienceComment") ExperienceComment experienceComment, final BindingResult binding) {
		ModelAndView result;

		try {
			experienceComment = this.experienceCommentService.reconstruct(experienceComment, binding);
			if (binding.hasErrors()) {
				result = this.createModelAndView(experienceComment);
				for (final ObjectError e : binding.getAllErrors())
					System.out.println(e.getObjectName() + " error [" + e.getDefaultMessage() + "] " + Arrays.toString(e.getCodes()));
			} else {
				experienceComment = this.experienceCommentService.save(experienceComment);
				result = new ModelAndView("redirect:/welcome/index.do");
			}

		} catch (final Throwable oops) {
			result = this.createModelAndView(experienceComment, "experienceComment.commit.error");
		}
		return result;
	}
	
	//Ancillary methods--------------
	
	private ModelAndView createModelAndView(final ExperienceComment experienceComment) {
		ModelAndView result;

		result = this.createModelAndView(experienceComment, null);
		return result;
	}

	private ModelAndView createModelAndView(final ExperienceComment experienceComment, final String messageCode) {
		ModelAndView result;
		result = new ModelAndView("experienceComment/create");
		result.addObject("experienceComment", experienceComment);
		result.addObject("message", messageCode);
		return result;
	}

}
