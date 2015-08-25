package dstakhov.socialnetwork.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import dstakhov.socialnetwork.dao.DaoFactory;
import dstakhov.socialnetwork.entity.User;
import dstakhov.socialnetwork.logic.request.LoginRequest;
import dstakhov.socialnetwork.logic.result.LoginResult;
import dstakhov.socialnetwork.logic.result.LoginResult.Result;

/**
 * Authorization business logic.
 * @author Dmytro Stakhov
 */
@Component
public class LoginLogic {
	
	/** Dao factory. Use it for data storage purposes.*/
	private DaoFactory daoFactory;

	/**
	 * Execute logic and returns the execution result data.
	 * @param request - contains necessary values for execution.
	 * @return LoginResult with info about logic execution.
	 * @throws Exception if something went wrong.
	 */
	public LoginResult execute(final LoginRequest request) throws Exception {
		LoginResult result = LoginResult.getSuccessful();

		User user = getDaoFactory().getUserDao().getUserByUserNameAndPassword(
				request.getUsername(), request.getPassword());

		if (user == null) {
			result.setResult(Result.ERROR);
		} else {
			result.setUser(user);
		}

		return result;
	}

	/**
	 * Gets the daoFactory.
	 * @return daoFactory.
	 */
	public DaoFactory getDaoFactory() {
		return daoFactory;
	}

	/**
	 * Sets the daoFactory.
	 * @param daoFactory value to set.
	 */
	@Autowired
	public void setDaoFactory(final DaoFactory daoFactory) {
		this.daoFactory = daoFactory;
	}
}
