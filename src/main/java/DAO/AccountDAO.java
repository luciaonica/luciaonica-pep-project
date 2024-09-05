package DAO;

import Model.Account;
import java.sql.*;
import Util.ConnectionUtil;

public class AccountDAO {

    /**
     * register new account
     */
    public Account createAcount(Account account) {

        Connection connection = ConnectionUtil.getConnection();

        try {

            //sql query for creating an account
            String sql = "insert into account(username, password) values (?, ?);" ;
            
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            
            preparedStatement.setString(1, account.getUsername());
            preparedStatement.setString(2, account.getPassword());
        
            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();

            if(rs.next()){
                //getting the id on newly created account
                int account_id = (int) rs.getInt(1);

                return new Account(account_id, account.getUsername(), account.getPassword());
            }
        } catch(SQLException e){
            System.out.println(e.getMessage());
        }

        //return null if something goes wrong
        return null;
    }

    /**
     * retrieve account by username
     * @param username
     * @return account identified by username
     */
    public Account getAccountByUsername(String username) {
        Connection connection = ConnectionUtil.getConnection();

        try {
            String sql = "select * from account where username=?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, username);

            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next()){
                Account acc = new Account(rs.getInt("account_id"),
                        rs.getString("username"),
                        rs.getString("password"));
                return acc;
            }
        } catch(SQLException e){
            System.out.println(e.getMessage());
        }

        //return null if no account is found
        return null;
    }

    /**
     * retrieve account by account_id
     * @param id
     * @return account identified by account_id
     */
    public Account getAccountById(int id) {
        Connection connection = ConnectionUtil.getConnection();

        try {
            String sql = "select * from account where account_id=?;";
	        PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, id);

	        ResultSet rs = preparedStatement.executeQuery();

            while(rs.next()){
                Account acc = new Account(rs.getInt("account_id"),
                    rs.getString("username"),
                    rs.getString("password"));
                return acc;
            }
        } catch(SQLException e){
            System.out.println(e.getMessage());
        }

        //return null if no account is found
        return null;
    }
}