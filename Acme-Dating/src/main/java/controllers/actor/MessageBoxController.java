
package controllers.actor;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.MessageBoxService;
import controllers.AbstractController;
import domain.Actor;
import domain.Message;
import domain.MessageBox;

@Controller
@RequestMapping("/messageBox/actor")
public class MessageBoxController extends AbstractController {

	// Services

	@Autowired
	private MessageBoxService	messageBoxService;

	@Autowired
	private ActorService		actorService;


	// Listing

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		final Collection<MessageBox> boxes;
		Actor principal;

		principal = this.actorService.findByPrincipal();

		boxes = principal.getMessageBoxes();

		result = new ModelAndView("messageBox/list");
		result.addObject("messageBoxes", boxes);

		return result;
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET, params = {
		"messageBoxId"
	})
	public ModelAndView list(@RequestParam final int messageBoxId) {
		final ModelAndView result;
		MessageBox currentBox;
		Collection<Message> messages;

		currentBox = this.messageBoxService.findOne(messageBoxId);
		messages = currentBox.getMessages();

		result = new ModelAndView("messageBox/list");
		result.addObject("currentMessageBox", currentBox);
		result.addObject("messages", messages);

		return result;
	}
}
