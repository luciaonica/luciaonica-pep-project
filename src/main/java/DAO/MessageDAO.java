package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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

    public List<Message> getAllMessages() {
		Connection connection = ConnectionUtil.getConnection();
		List<Message> messages = new ArrayList<>();
        try {
        	String sql = "select * from message;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
            	Message message = new Message(rs.getInt("message_id"), rs.getInt("posted_by"), rs.getString("message_text"), rs.getLong("time_posted_epoch"));
            	messages.add(message);              
               
            }
        	
        } catch(SQLException e){
            System.out.println(e.getMessage());
        }
        
        return messages;
	}

    public Message getMessageById(int id) {
		Connection connection = ConnectionUtil.getConnection();
		try {
        	String sql = "select * from message where message_id=?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            
            preparedStatement.setInt(1, id);
            
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
            	return new Message(rs.getInt("message_id"), rs.getInt("posted_by"), rs.getString("message_text"), rs.getLong("time_posted_epoch"));
            	               
            }
        	
        } catch(SQLException e){
            System.out.println(e.getMessage());
        }
		
		return null;
	}

    public Message deleteMessageById(int id) {
		Connection connection = ConnectionUtil.getConnection();
		try {
        	String sql = "delete from message where message_id=?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            
            preparedStatement.setInt(1, id);
            
            Message message = getMessageById(id);
            int rowsDeleted = preparedStatement.executeUpdate();
            if (rowsDeleted > 0) {            	
            	return message;
            }
        } catch(SQLException e){
            System.out.println(e.getMessage());
        }
		return null;
    }

    public Message updateMessage(int id, Message newMessage) {
		
		Connection connection = ConnectionUtil.getConnection();
		try {
		
			String sql = "UPDATE message SET message_text=? WHERE message_id=?";
			
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
	        
	        preparedStatement.setInt(2, id);
	        preparedStatement.setString(1, newMessage.getMessage_text());
	        
	        int rowsUpdated = preparedStatement.executeUpdate();
	        
	        if (rowsUpdated > 0) {
	        	Message message = getMessageById(id);
            	System.out.println("MessageDaO updated ");
            	return message;
            }
		} catch(SQLException e){
            System.out.println(e.getMessage());
        }
		
		return null;
	}
            
}
