package Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Model.Account;
import Model.Message;
import io.javalin.Javalin;
import io.javalin.http.Context;
import Service.AccountService;
import Service.MessageService;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */

     AccountService accountService;
     MessageService messageService;

    public SocialMediaController(){
        this.accountService = new AccountService();
        this.messageService = new MessageService();
    }


    public Javalin startAPI() {

        Javalin app = Javalin.create();

        app.get("example-endpoint", this::exampleHandler);

        app.post("/register",this::userRegistrationHandler);

        app.post("/login",this::loginHandler);

        app.post("/messages",this::createMessageHandler);

        app.get("/messages",this::getAllMessagesHandler);

        app.get("/messages/{message_id}",this::getOneMessageHandler);

        app.delete("/messages/{message_id}",this::deleteMessageHandler);

        app.patch("/messages/{message_id}",this::updateMessageHandler);

        app.get("/accounts/{account_id}/messages",this::getAllUserMessagesHandler);

        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void exampleHandler(Context context) {
        context.json("sample text");
    }

    private void userRegistrationHandler(Context context) throws JsonProcessingException{

        ObjectMapper map = new ObjectMapper();

        Account newAccount = map.readValue(context.body(), Account.class);

        Account addAccount = accountService.registerAccount(newAccount);
                
        if (addAccount != null){
            context.json(map.writeValueAsString(addAccount));
            
        } else{
            context.status(400);
        }
        
    }
    
    private void loginHandler(Context context) throws JsonProcessingException{

        ObjectMapper map = new ObjectMapper();

        Account login_Account = map.readValue(context.body(), Account.class);

        if(accountService.Login(login_Account) != null){
            Account jsonAccount = accountService.checkExistingAccount(login_Account);
            context.json(map.writeValueAsString(jsonAccount));
        }else{
            context.status(401);
        }
    }

    private void createMessageHandler(Context context) throws JsonProcessingException{
        ObjectMapper map = new ObjectMapper();

        Message newMessage = map.readValue(context.body(), Message.class);

        Message addMessage = messageService.createMessage(newMessage);

        if (addMessage != null){
            context.json(map.writeValueAsString(addMessage));
            
        } else{
            context.status(400);
        }
        

    }

    private void getAllMessagesHandler(Context context){

        context.json(messageService.getAllMessages());
            
    }

    private void getOneMessageHandler(Context context) {

        String message_id = context.pathParam("message_id");
        Message message = messageService.getOneMessage(message_id);
        if (message != null){
            context.json(message);
        }
    
            
    }


    private void deleteMessageHandler(Context context) {

        
        String message_id = context.pathParam("message_id");
        Message message = messageService.deleteMessage(message_id);
        
        if (message != null){
            context.json(message);
        }
    
            
    }

    private void updateMessageHandler(Context context) throws JsonProcessingException{
        
        String message_id = context.pathParam("message_id");

        ObjectMapper map = new ObjectMapper();

        Message message_for_text = map.readValue(context.body(), Message.class);
    
        Message message = messageService.updateMessage(message_id,message_for_text.message_text);
        if (message != null){
            context.json(message);
        }else{
            context.status(400);

        }
    
            
    }

    private void getAllUserMessagesHandler(Context context) throws JsonProcessingException{
        
        String account_id = context.pathParam("account_id");
        
        context.json(messageService.getAllUserMessages(account_id));
       
    }

    


}