package Controller;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
import io.javalin.Javalin;
import io.javalin.http.Context;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {

    private AccountService accountService;
    private MessageService messageService;

    public SocialMediaController() {
		this.accountService = new AccountService();
        this.messageService = new MessageService();
	}

    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.get("example-endpoint", this::exampleHandler);
        app.post("register", this::createAccountHandler);
        app.post("login", this::loginHandler);
        app.post("messages", this::createMessageHandler);
        app.get("messages", this::getAllMessagesHandler);
        app.get("messages/{message_id}", this::getMessageByIdHandler);
        app.delete("messages/{message_id}", this::deleteMessageByIdHandler);
        app.patch("messages/{message_id}", this::updateMessageByIdHandler);
        app.get("accounts/{account_id}/messages", this::getAllMessagesByAccountIdHandler);

        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void exampleHandler(Context context) {
        context.json("sample text");
    }

    /**
     * Handler to create new account
     * @param ctx
     * @throws JsonProcessingException
     */
    private void createAccountHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account acc = mapper.readValue(ctx.body(), Account.class);
        Account addedAccount = accountService.createAccount(acc);
        if (addedAccount != null) {
            ctx.json(mapper.writeValueAsString(addedAccount));
        } else {
            ctx.status(400);
        }
    }

    /**
     * handler to login user
     * @param ctx
     * @throws JsonProcessingException
     */
    private void loginHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account acc = mapper.readValue(ctx.body(), Account.class);
        Account loginSuccess = accountService.login(acc);
        if (loginSuccess != null) {
            ctx.json(mapper.writeValueAsString(loginSuccess));
        } else {
            ctx.status(401);
        }
    }

    /**
     * handler to post a new message
     */
    private void createMessageHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class);
        
        Message addedMessage = messageService.addMessage(message);
        
        if (addedMessage != null) {
        	ctx.json(mapper.writeValueAsString(addedMessage));
        } else {
        	ctx.status(400);
        }
    }

    /**
     * HAndler to retrieve all messages
     * @param ctx
     */
    private void getAllMessagesHandler(Context ctx)  {
    	List<Message> messages = messageService.getAllMessages();
        ctx.json(messages);
    }

    /**
     * Handler to retrieve a message with a given id. If no message exists with this id return empty body.
     * @param ctx
     */
    private void getMessageByIdHandler(Context ctx)  {
    	
    	int id = Integer.valueOf(ctx.pathParam("message_id"));	
    	
    	Message message = messageService.getMessageById(id);
        
    	if (message != null) {
    		ctx.json(message);
    	} else {
    		ctx.result("");
    	}
    }

    /**
     * Handler to delete a message with given id. If no message exists with this id return empty body.
     * @param ctx
     */
    private void deleteMessageByIdHandler(Context ctx)  {
    	
    	int val = Integer.valueOf(ctx.pathParam("message_id"));
    	
    	Message message = messageService.deleteMessageById(val);
    	if (message != null) {
    		ctx.json(message);
    	} else {
    		ctx.result("");
    	}
    }

    /**
     * Handler to update the message text by given id
     * @param ctx
     * @throws JsonProcessingException
     */
    private void updateMessageByIdHandler(Context ctx) throws JsonProcessingException  {
    	int message_id = Integer.valueOf(ctx.pathParam("message_id"));
    	
    	ObjectMapper mapper = new ObjectMapper();

        Message newMessage = mapper.readValue(ctx.body(), Message.class);
        
        Message updatedMessage = messageService.updateMessage(message_id, newMessage);
        
        if (updatedMessage != null) {
        	ctx.json(mapper.writeValueAsString(updatedMessage));
        } else {
        	ctx.status(400);
        }
    }

    /**
     * Handler to retrieve all messages posted by user with given account id
     * @param ctx
     */
    private void getAllMessagesByAccountIdHandler(Context ctx)  {
    	int id = Integer.valueOf(ctx.pathParam("account_id"));
    	List<Message> messages = messageService.getAllMessagesByAccountId(id);
        ctx.json(messages);
    }

}