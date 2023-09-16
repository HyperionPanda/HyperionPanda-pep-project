package Service;

import Model.Message;
import DAO.MessageDAO;
import java.util.List;


public class MessageService {

    private MessageDAO messageDAO;
   

    public MessageService (){
        this.messageDAO = new MessageDAO();
        
    }

    public Message createMessage(Message message){
        
        if (message.getMessage_text() != "" && (message.getMessage_text()).length() < 255 && messageDAO.userExists(message) ){
            return messageDAO.createNewMessage(message);
        }else{
            return null;
        }
        
    }

    public List<Message> getAllMessages(){
        return messageDAO.getAllMessages();  
    }

    public Message getOneMessage(String message_id){
        return messageDAO.getOneMessage(message_id);  
    }

    public Message deleteMessage(String message_id){
        return messageDAO.deleteMessage(message_id);  
    }

    public Message updateMessage(String message_id, String message_text){
        Message message_Check = getOneMessage(message_id);
        if (message_Check != null  && message_text.length() <  255 && message_text != ""){
            return messageDAO.updateMessage(message_id, message_text); 

        }
        return null;
    }
    public List<Message> getAllUserMessages(String account_id){
        return messageDAO.getAllUserMessages(account_id);
    }
    
}
