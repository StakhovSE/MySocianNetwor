package dstakhov.socialnetwork.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import dstakhov.socialnetwork.dao.DaoFactory;
import dstakhov.socialnetwork.entity.User;
import dstakhov.socialnetwork.logic.request.RegistrationRequest;
import dstakhov.socialnetwork.logic.result.RegistrationResult;
import dstakhov.socialnetwork.logic.result.RegistrationResult.Result;

/**
 * Registration business logic.
 * @author Dmytro Stakhov
 */
@Component
public class RegistrationLogic {
	
	/** Dao factory. Use it for data storage purposes.*/
	private DaoFactory daoFactory;

	/**
	 * Execute logic and returns the execution result data.
	 * @param request - contains necessary values for execution.
	 * @return RegistrationResult with info about logic execution.
	 * @throws Exception if something went wrong.
	 */
	public RegistrationResult execute(final RegistrationRequest request) throws Exception {

		RegistrationResult result = RegistrationResult.getSuccessfull();

		if(!request.getPassword().equals(request.getRepeatPassword())) {
			result.setResult(Result.ERROR);
		}

		User user = new User();
		user.setUsername(request.getLogin());
		user.setPassword(request.getPassword());

		user = daoFactory.getUserDao().saveNewAndGet(user);
		
		if(user == null) {
			result.setResult(Result.ERROR);
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
	 * Gets the daoFactory.
	 * @return daoFactory.
	 */
	@Autowired
	public void setDaoFactory(final DaoFactory daoFactory) {
		this.daoFactory = daoFactory;
	}
}
