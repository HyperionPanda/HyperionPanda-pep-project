package DAO;

import Model.Account;
import Util.ConnectionUtil;
import java.util.ArrayList;
import java.sql.*;
import java.util.List;

public class AccountDAO {

    public List<Account> getAllAccounts(){

        Connection connection = ConnectionUtil.getConnection();
        List<Account> accounts = new ArrayList<>();

        try{
            String newSQL = "select * from Account;";
            PreparedStatement stm = connection.prepareStatement(newSQL);
            ResultSet results = stm.executeQuery();
            while(results.next() != false){
                Account newAccount = new Account(results.getString("username"), results.getString("password"));
                accounts.add(newAccount);
            }

        }catch (SQLException e){
            System.out.println("Error with "+e.getMessage()+" with a code of ");
            System.out.print(e.getErrorCode());

        }
        return accounts;

    }
    
}
