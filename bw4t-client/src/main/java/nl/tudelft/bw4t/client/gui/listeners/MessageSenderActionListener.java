package nl.tudelft.bw4t.client.gui.listeners;

import java.awt.event.ActionEvent;

import org.apache.log4j.Logger;

import eis.exceptions.ActException;
import eis.iilang.Identifier;
import eis.iilang.Percept;
import nl.tudelft.bw4t.client.controller.ClientController;
import nl.tudelft.bw4t.client.controller.ClientMapController;
import nl.tudelft.bw4t.client.message.BW4TMessage;
import nl.tudelft.bw4t.client.message.MessageTranslator;
import nl.tudelft.bw4t.map.view.ViewEntity;
/**
 * ActionListener that sends a message when the connected menu item is pressed.
 */
public class MessageSenderActionListener extends AbstractClientActionListener {
    /** Message to send when this listener is fired. */
    private final BW4TMessage message;

    /** Logger to report error messages to. */
    private static final Logger LOGGER = Logger.getLogger(MessageSenderActionListener.class);

    /**
     * @param message - The {@link BW4TMessage} to send when this listener is fired.
     * @param controller - The {@link ClientController} to listen to and interact with.
     */
    public MessageSenderActionListener(BW4TMessage message, ClientController controller) {
        super(controller);
        this.message = message;
    }

    @Override
    public void actionWithHumanAgent(ActionEvent e) {
        ClientController controller = getController();
        ClientMapController cmp = controller.getMapController();
        ViewEntity bot = cmp.getTheBot();
    	String ownName = bot.getName();
        /** Sends the message to the receiver(s): */
        sendMessagesHuman(ownName, new String[] {findReceiver(ownName)});
    }
    
    @Override
    public void actionWithGoalAgent(ActionEvent e) {
    	String ownName = getController().getMapController().getTheBot().getName();
        /** Sends the message to the receiver(s): */
        String receiver = findReceiver(ownName);
    	
    	if ("all".equals(receiver)) {
        	receiver="allother"; // do not send message to yourself. 
        }
        sendMessagesGoal(ownName, new String[] {receiver});
    }
    
    /**
     * Finds the names of the receivers of the message:
     * @param ownName
     * @return the receivers of the message
     */
    private String findReceiver(String ownName) {
        /** Finds the names of the receivers of the message: */
       return (String) getController().getGui().getAgentSelector().getModel().getSelectedItem();
       // String[] receivers = new String[] {ownName, receiver};
       // if ("all".equals(receiver) || ownName.equals(receiver)) {
       //     receivers = new String[] {receiver};
       // }
       // return receivers;
    }
    
    
    /**
     * Sends the message from Human to the receiver(s): 
     * @param ownName 
     * @param receivers 
     */
    private void sendMessagesHuman(String ownName, String[] receivers) {
        for (String name : receivers) {
        	try {
                getController().getHumanAgent().sendMessage(name, message);
            } catch (ActException e1) {
                LOGGER.error("Could not send message to all other bots.", e1);
            }
        }
    }
    
    /**
     * Sends the message from Human connected to GOAL to the receiver(s):
     * @param ownName
     * @param receivers
     */
    private void sendMessagesGoal(String ownName, String[] receivers) {
    	for (String name : receivers) {
             Percept percept = new Percept("sendMessage", new Identifier(name), MessageTranslator.translateMessage(
                     message, ownName));
             getController().addToBePerformedAction(percept);
    	}
    }
}
