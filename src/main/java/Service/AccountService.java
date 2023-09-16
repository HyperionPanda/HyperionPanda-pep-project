package Service;

import DAO.AccountDAO;
import Model.Account;

public class AccountService {
    
    private AccountDAO accountDAO;

    public AccountService (){
        this.accountDAO = new AccountDAO();
    }

    public Account registerAccount(Account account){
        
        String password = account.getPassword();
        String username = account.getUsername();
        if (password.length() > 3 && username != ""){
            return accountDAO.registerAccount(account);
            
        }else{
            return null;
        }
    }
    
    public Account Login(Account account){
        return accountDAO.Login(account);
    }
    
    public Account checkExistingAccount(Account account){
        return accountDAO.accountExists(account);
    }
    
}
