package Service;

import DAO.AccountDAO;
import Model.Account;

public class AccountService {

    private AccountDAO accountDAO;

    public AccountService(){
        accountDAO = new AccountDAO();
    }

    public Account createAccount(Account account) {
		
		
		if (account.getUsername().trim().length() == 0) {
			return null;
		}
		
		if (account.getPassword().length() < 4) {
			return null;
		}
		
	    if (accountDAO.getAccountByUsername(account.getUsername()) != null){
            return null;
        }
	
		return accountDAO.createAcount(account);		
		
    }

    public Account login(Account account) {
        Account accountFromDB = accountDAO.getAccountByUsername(account.getUsername());

        if (accountFromDB != null && accountFromDB.getPassword().equals(account.getPassword())){
            return accountFromDB;
        }

        return null;
    }
    
}
