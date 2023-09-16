package DAO;

import Model.Message;
import Util.ConnectionUtil;
import java.util.ArrayList;
import java.sql.*;
import java.util.List;

public class MessageDAO {

    public Message createNewMessage(Message message){
        Connection connection = ConnectionUtil.getConnection();
        try{
            String create_Message = "INSERT INTO Message (posted_by, message_text, time_posted_epoch) VALUES (?, ?, ?);";
            PreparedStatement ps = connection.prepareStatement(create_Message, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, message.getPosted_by());
            ps.setString(2, message.getMessage_text());
            ps.setLong(3, message.getTime_posted_epoch());

            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();

            if(rs.next()){
                int messageID = (int) rs.getInt(1);
                Message msg = new Message(messageID,message.getPosted_by(), message.getMessage_text(), message.getTime_posted_epoch());
                return msg;

            }

        }catch (SQLException e){
            System.out.println(e.getMessage());
            System.out.println(e.getErrorCode());
        }
        return null;
    }

    public Boolean userExists(Message message){
        Connection connection = ConnectionUtil.getConnection();
        try{
            
            String check_user = "SELECT * FROM Account WHERE ? = account_id;";
            PreparedStatement ps = connection.prepareStatement(check_user);
            ps.setInt(1, message.getPosted_by());
            

          
            ResultSet rs =  ps.executeQuery();

            if(rs.next()){
                return true;

            }

        }catch (SQLException e){
            System.out.println(e.getMessage());
            System.out.println(e.getErrorCode());
        }
        return false;
    }

    public List<Message> getAllMessages(){
        Connection connection  = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();
        try{
            String sql = " SELECT * FROM Message;";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
           while(rs.next()){
                Message newMessage = new Message(rs.getInt("message_id"), rs.getInt("posted_by"), rs.getString("message_text"), rs.getLong("time_posted_epoch"));
                messages.add(newMessage);

            }

        }catch(SQLException e){
            System.out.println(e.getMessage());
            System.out.println(e.getErrorCode());
        }
        return messages;
    }


    public Message getOneMessage(String message_id){
        Connection connection  = ConnectionUtil.getConnection();
        
        try{
            String sql = " SELECT * FROM Message WHERE message_id = ?;";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1,message_id);
            ResultSet rs = ps.executeQuery();
           if(rs.next()){
                Message newMessage = new Message(rs.getInt("message_id"), rs.getInt("posted_by"), rs.getString("message_text"), rs.getLong("time_posted_epoch"));
                return newMessage;

            }

        }catch(SQLException e){
            System.out.println(e.getMessage());
            System.out.println(e.getErrorCode());
        }
        return null;
    }

    public Message deleteMessage(String message_id){
        Connection connection  = ConnectionUtil.getConnection();
        
        try{
            String initial_sql = "SELECT * FROM Message WHERE message_id = ?;";
            PreparedStatement initial_ps = connection.prepareStatement(initial_sql);
            initial_ps.setString(1,message_id);
            ResultSet rs = initial_ps.executeQuery();

            String sql = " DELETE FROM Message WHERE message_id = ?;";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1,message_id);
            
            ps.executeUpdate();
           if(rs.next()){
                Message deletedMessage = new Message(rs.getInt("message_id"), rs.getInt("posted_by"), rs.getString("message_text"), rs.getLong("time_posted_epoch"));
                return deletedMessage;

            }

        }catch(SQLException e){
            System.out.println(e.getMessage());
            System.out.println(e.getErrorCode());
        }
        return null;
    }

    public Message updateMessage(String message_id, String message_text){
        Connection connection  = ConnectionUtil.getConnection();
        
        try{
            String initial_sql = "UPDATE Message SET message_text = ? where message_id = ?;";
            PreparedStatement initial_ps = connection.prepareStatement(initial_sql);
            initial_ps.setString(1,message_text);
            initial_ps.setString(2,message_id);
            initial_ps.executeUpdate();

            String sql = "SELECT * FROM Message WHERE message_id = ?;";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1,message_id);
            ResultSet rs = ps.executeQuery();
            
           if(rs.next()){
                Message updatedMessage = new Message(rs.getInt("message_id"), rs.getInt("posted_by"), rs.getString("message_text"), rs.getLong("time_posted_epoch"));
                return updatedMessage;

            }

        }catch(SQLException e){
            System.out.println(e.getMessage());
            System.out.println(e.getErrorCode());
        }
        return null;
    }

    public ArrayList<Message> getAllUserMessages(String account_id){
        Connection connection  = ConnectionUtil.getConnection();
        ArrayList<Message> message_list = new ArrayList<>();
        try{
            String sql = "SELECT * FROM Message WHERE posted_by = ?;";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1,account_id);
            ResultSet rs = ps.executeQuery();

            
           while(rs.next()){
                Message message = new Message(rs.getInt("message_id"), rs.getInt("posted_by"), rs.getString("message_text"), rs.getLong("time_posted_epoch"));
                message_list.add(message);

            }

        }catch(SQLException e){
            System.out.println(e.getMessage());
            System.out.println(e.getErrorCode());
        }
        return message_list;
    }
    
}
