package dstakhov.socialnetwork.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import dstakhov.socialnetwork.logic.GetUserLogic;
import dstakhov.socialnetwork.logic.request.GetUserRequest;
import dstakhov.socialnetwork.logic.result.GetUserResult;
import dstakhov.socialnetwork.logic.result.GetUserResult.Result;

/**
 * Spring MVC controller which takes care about actions with user profile.
 * @author Dmytro Stakhov
 */
@Controller
public class ProfileController {

	/** Provide fetching user from data storage. */
	@Autowired
	private GetUserLogic getUserLogic;

	/**
	 * Executes getUser logic, gets the view name depends on result.
	 * Possible values for view: profile, error.
	 * 
	 * @param login got from view
	 * @param password got from view
	 * @param session
	 * @return view name
	 */
	@RequestMapping("profile")
	public String getProfile(Model model, HttpSession session) {

		GetUserRequest request = new GetUserRequest();
		request.setUsername((String) session.getAttribute("username"));
		GetUserLogic logic = new GetUserLogic();

		try {
			GetUserResult result = logic.execute(request);
			if (Result.SUCCESS.equals(result.getResult())) {
				model.addAttribute("user", result.getUser());
			} else {
				return "error";
			}
		} catch(Exception e) {
			return "error";
		}

		return "profile";
	}
}
