package Service;

import java.util.List;

import DAO.AccountDAO;
import DAO.MessageDAO;
import Model.Account;
import Model.Message;

public class MessageService {
    private MessageDAO messageDAO;
	private AccountDAO accountDAO;

    /**
     * no-args constructor for creating MessageService with new AccountDAO and MessageDAO
     */
    public MessageService() {
		this.messageDAO = new MessageDAO();
		this.accountDAO = new AccountDAO();
	}

    /**
     * Constructor for a MessageService when AccountDAO and MessageDAO are provided.
    * @param messageDAO
    * @param accountDAO
    */
    public MessageService(MessageDAO messageDAO, AccountDAO accountDAO) {
		this.messageDAO = messageDAO;
		this.accountDAO = accountDAO;
	}

    /**
     * add a message to DB
     * @param message a message object
     * @return The persisted message if the persistence is successful.
     */
    public Message addMessage(Message message) {

        //validating message not blank
        if (message.getMessage_text().trim().length() == 0) {			
			return null;
		}

        //validating message length
        if (message.getMessage_text().length() >= 255) {
			return null;
		}

        Account account = accountDAO.getAccountById(message.getPosted_by());

        //validating existing account
        if (account == null){
            return null;
        }

        return messageDAO.createMessage(message);
    }

    /**
     * retrieve all messages from DB
     * @return A list of messages
     */
    public List<Message> getAllMessages() {
        return messageDAO.getAllMessages();
    }

    /**
     * retrieve a message by given message_id
     * @param id The message id
     * @return the message with given id if it is found
     */
    public Message getMessageById(int id) {
        return messageDAO.getMessageById(id);
    }

    /**
     * delete message by given id
     * @param id
     * @return the deleted message, if the deletion is successfull
     */
    public Message deleteMessageById(int id) {
        return messageDAO.deleteMessageById(id);
    }

    /**
     * update message text by given id
     */
    public Message updateMessage(int id, Message newMessage) {
		
		Message message = messageDAO.getMessageById(id);
		
        //validate if message with given id exists in DB
		if (message == null) {			
			return null;
		}
		
        //validate if the new message text is not blank
		if (newMessage.getMessage_text().trim().length() == 0) {			
			return null;
		}
		
        //validate if the new message text is less than 255 characters
		if (newMessage.getMessage_text().length() >= 255) {			
			return null;
		}

		return messageDAO.updateMessage(id, newMessage);
	}

    /**
     * retrieve all messages by given account id
     * @param id
     * @return all messages posted by user with given account id
     */
    public List<Message> getAllMessagesByAccountId(int id) {
        return messageDAO.getAllMessagesByAccountId(id);
    }
}
