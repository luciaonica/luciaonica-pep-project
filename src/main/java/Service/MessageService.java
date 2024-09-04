package Service;

import java.util.List;

import DAO.AccountDAO;
import DAO.MessageDAO;
import Model.Account;
import Model.Message;

public class MessageService {
    private MessageDAO messageDAO;
	private AccountDAO accountDAO;

    public MessageService() {
		this.messageDAO = new MessageDAO();
		this.accountDAO = new AccountDAO();
	}

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

    public List<Message> getAllMessages() {
        return messageDAO.getAllMessages();
    }
}
