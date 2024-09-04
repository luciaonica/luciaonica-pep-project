package DAO;

import java.sql.*;

import Model.Message;
import Util.ConnectionUtil;

public class MessageDAO {
    public Message createMessage(Message message){

        Connection connection = ConnectionUtil.getConnection();
        try {

            //SQL query for adding a message to DB

            String sql = "insert into message(posted_by, message_text, time_posted_epoch) values(?,?,?);" ;
        
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
  
            preparedStatement.setInt(1, message.getPosted_by());
            preparedStatement.setString(2, message.getMessage_text());
            preparedStatement.setLong(3, message.getTime_posted_epoch());

            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();

            if (rs.next()) {
                int message_id = (int) rs.getInt(1);
                return new Message(message_id, message.getPosted_by(), message.getMessage_text(), message.getTime_posted_epoch());
            }

        } catch(SQLException e){
            System.out.println(e.getMessage());
        }
        //if something is wrong return null
        return null;
    }
}
