package DAO;

import Model.Account;
import Util.ConnectionUtil;
import java.sql.*;

public class AccountDAO {

    public Account registerAccount(Account account){
        Connection connection = ConnectionUtil.getConnection();
        try{


            String sql = "INSERT into Account (username, password) values (?,?);";

            //Needs return generated keys?
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, account.getUsername());
            ps.setString(2, account.getPassword());

            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if(rs.next()){
                int accountID = (int) rs.getInt(1);
                Account newAccount = new Account(accountID, account.getUsername(),account.getPassword());
                return newAccount;
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Account accountExists(Account account){
        Connection connection = ConnectionUtil.getConnection();
        try{


            String sql = "SELECT * FROM Account WHERE username = ?;";

            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, account.getUsername());

            ResultSet rs = ps.executeQuery();


            while(rs.next()){
                Account existingAcc = new Account(rs.getInt("account_id"),rs.getString("username"), rs.getString("password"));
                return existingAcc;
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }
     
    public Account Login(Account account){
        Connection connection = ConnectionUtil.getConnection();
        try{

            String sql = "SELECT * FROM Account WHERE username = ? and password = ?;";
            PreparedStatement ps = connection.prepareStatement(sql);

            
            ps.setString(1, account.getUsername());
            ps.setString(2, account.getPassword());

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                Account loggedIn = new Account(rs.getString("username"), rs.getString("password"));
                return loggedIn;
                
            }

        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }
    
    
}
