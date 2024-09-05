package Service;

import DAO.AccountDAO;
import Model.Account;

public class AccountService {

    private AccountDAO accountDAO;

    /**
     * no-args constructor for creating AccountService with a new AccountDAO
     */
    public AccountService(){
        accountDAO = new AccountDAO();
    }

    /**
     * Constructor for a AccountService when a AccountDAO is provided.
     * @param accountDAO
     */
    public AccountService(AccountDAO accountDAO){
        this.accountDAO = accountDAO;
    }

    /**
     * create a new account
     * @param account an account object
     * @return the persisted account if the persistence is successful
     */
    public Account createAccount(Account account) {		
		
        //validate username not blank
		if (account.getUsername().trim().length() == 0) {
			return null;
		}
		
        //validate password at least 4 characters
		if (account.getPassword().length() < 4) {
			return null;
		}
		
        //validate the username is unique
	    if (accountDAO.getAccountByUsername(account.getUsername()) != null){
            return null;
        }
	
		return accountDAO.createAcount(account);		
    }

    /**
     * perform login
     */
    public Account login(Account account) {

        //retrieve account from DB by given username
        Account accountFromDB = accountDAO.getAccountByUsername(account.getUsername());

        //validate creadentials
        if (accountFromDB != null && accountFromDB.getPassword().equals(account.getPassword())){
            return accountFromDB;
        }

        return null;
    }
    
}
