package controllers.couple;

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

import repositories.UserRepository;
import security.LoginService;
import security.UserAccount;
import services.RecordCommentService;

import controllers.AbstractController;
import domain.Couple;
import domain.RecordComment;
import domain.User;

@Controller
@RequestMapping("/recordComment")
public class RecordCommentController extends AbstractController {

	// Services

	@Autowired
	private RecordCommentService recordCommentService;

	@Autowired
	private UserRepository userRepository;

	// Create comentario raiz

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int recordId) {
		ModelAndView result;
		RecordComment recordComment;

		recordComment = this.recordCommentService.create(true, recordId);

		result = this.createModelAndView(recordComment);

		return result;
	}

	// Create con comentario padre
	@RequestMapping(value = "/createReply", method = RequestMethod.GET)
	public ModelAndView createReply(@RequestParam final int recordCommentId) {
		ModelAndView result;
		RecordComment recordComment;

		recordComment = this.recordCommentService
				.create(false, recordCommentId);

		result = this.createModelAndView(recordComment);

		return result;
	}

	// SAVE DE COMENTARIO RAIZ

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView createFinal(
			@ModelAttribute("recordComment") RecordComment recordComment,
			@RequestParam final int recordId, final BindingResult binding) {
		ModelAndView result;
		try {
			recordComment = this.recordCommentService.reconstruct(
					recordComment, true, recordId, binding);
			if (binding.hasErrors()) {
				result = this.createModelAndView(recordComment);
				for (final ObjectError e : binding.getAllErrors())
					System.out.println(e.getObjectName() + " error ["
							+ e.getDefaultMessage() + "] "
							+ Arrays.toString(e.getCodes()));
			} else {
				recordComment = this.recordCommentService.save(recordComment);
				result = new ModelAndView("redirect:/welcome/index.do");
			}
		} catch (final Throwable oops) {
			result = this.createModelAndView(recordComment,
					"recordComment.commit.error");
		}
		return result;
	}

	// SAVE DE COMENTARIO HIJO
	@RequestMapping(value = "/createReply", method = RequestMethod.POST, params = "save")
	public ModelAndView createHijo(
			@ModelAttribute("recordComment") RecordComment recordComment,
			@RequestParam final int recordCommentId, final BindingResult binding) {
		ModelAndView result;

		try {
			recordComment = this.recordCommentService.reconstruct(
					recordComment, false, recordCommentId, binding);
			if (binding.hasErrors()) {
				result = this.createModelAndView(recordComment);
				for (final ObjectError e : binding.getAllErrors())
					System.out.println(e.getObjectName() + " error ["
							+ e.getDefaultMessage() + "] "
							+ Arrays.toString(e.getCodes()));
			} else {
				recordComment = this.recordCommentService.save(recordComment);
				result = new ModelAndView("redirect:/welcome/index.do");
			}

		} catch (final Throwable oops) {
			result = this.createModelAndView(recordComment,
					"recordComment.commit.error");
		}
		return result;
	}

	// Ancillary methods--------------

	private ModelAndView createModelAndView(final RecordComment recordComment) {
		ModelAndView result;

		result = this.createModelAndView(recordComment, null);
		return result;
	}

	private ModelAndView createModelAndView(final RecordComment recordComment,
			final String messageCode) {
		ModelAndView result;

		if (recordComment.getRecordComment() != null) {
			result = new ModelAndView("recordComment/createReply");
		} else {
			result = new ModelAndView("recordComment/create");
		}

		final UserAccount userAccountPrincipal = LoginService.getPrincipal();
		User principal = this.userRepository
				.findByUserAccountId(userAccountPrincipal.getId());

		Couple couple = principal.getCouple();

		result.addObject("recordComment", recordComment);
		result.addObject("couple", couple);
		result.addObject("message", messageCode);
		return result;
	}

}
