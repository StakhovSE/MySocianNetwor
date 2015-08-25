package dstakhov.socialnetwork.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import dstakhov.socialnetwork.logic.LoginLogic;
import dstakhov.socialnetwork.logic.RegistrationLogic;
import dstakhov.socialnetwork.logic.request.LoginRequest;
import dstakhov.socialnetwork.logic.request.RegistrationRequest;
import dstakhov.socialnetwork.logic.result.LoginResult;
import dstakhov.socialnetwork.logic.result.LoginResult.Result;
import dstakhov.socialnetwork.logic.result.RegistrationResult;


/**
 * Spring MVC controller which takes care about registration and authorization.
 * @author Dmytro Stakhov
 */
@Controller
public class AuthorizationController {
	
	/** Provide authorization. */
	@Autowired
	private LoginLogic loginLogic;
	
	/** Provide registration. */
	@Autowired
	private RegistrationLogic registrationLogic;

	/**
	 * Mapping for login view.
	 * @return login
	 */
	@RequestMapping("login")
	public String getLoginPage() {
		return "login";
	}

	/**
	 * Executes login logic, gets the view name depends on result.
	 * Possible values for view: redirect:profile, redirect:login, error.
	 * 
	 * @param login got from view
	 * @param password got from view
	 * @param session
	 * @return view name
	 */
	@RequestMapping("doLogin")
	public String doLogin(@RequestParam(required = true) String login,
			@RequestParam(required = true) String password, HttpSession session) {
		LoginRequest request = new LoginRequest();
		request.setUsername(login);
		request.setPassword(password);

		try {
			LoginResult result = loginLogic.execute(request);

			if (Result.SUCCESS.equals(result.getResult())) {
				session.setAttribute("username", result.getUser().getUsername());
				return "redirect:profile";
			} else {
				return "redirect:login";
			}
		} catch (Exception e) {
			return "error";
		}
	}

	/**
	 * mapping for registration view.
	 * @return registration.
	 */
	@RequestMapping("registration")
	public String getRegistrationPage() {
		return "registration";
	}

	/**
	 * Executes registration logic, gets the view name depends on result.
	 * Possible values for view: redirect:registration, redirect:login, error.
	 * 
	 * @param login got from view
	 * @param password got from view
	 * @param session
	 * @return view name
	 */
	@RequestMapping("doRegistration")
	public String doRegistration(@RequestParam(required = true) String login,
			@RequestParam(required = true) String password,
			@RequestParam(required = true) String repeatPassword) {

		RegistrationRequest request = new RegistrationRequest();
		
		request.setLogin(login);
		request.setPassword(password);
		request.setRepeatPassword(repeatPassword);
		
		try {
			RegistrationResult result = registrationLogic.execute(request);
			if (dstakhov.socialnetwork.logic.result.RegistrationResult.Result.SUCCESS.equals(result.getResult())) {
				return "redirect:login";
			} else {
				return "redirect:registration"; 
			}
		} catch(Exception e) {
			return "error";
		}	
	}
}