package nl.tudelft.bw4t.client.gui.listeners;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.util.LinkedList;

import org.apache.log4j.Logger;

import nl.tudelft.bw4t.client.controller.ClientController;
import nl.tudelft.bw4t.client.environment.Launcher;
import eis.iilang.Numeral;
import eis.iilang.Percept;

/**
 * ActionListener that performs the goTo action when that command is pressed in
 * the pop up menu
 */
public class GotoPositionActionListener extends ClientActionListener {
    private final Point position;

    private static final Logger LOGGER = Logger.getLogger(GotoPositionActionListener.class);

    public GotoPositionActionListener(Point position, ClientController control) {
        super(control);
        this.position = position;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!Launcher.getEnvironment().isConnectedToGoal()) {
            try {
                getController().getHumanAgent().goTo(position.getX(), position.getY());
            } catch (Exception e1) {
            	LOGGER.error(e1); 
            }
        } else {
            LinkedList<Percept> percepts = new LinkedList<Percept>();
            Percept percept = new Percept("goTo", new Numeral(position.getX()), new Numeral(position.getY()));
            percepts.add(percept);
            getController().setToBePerformedAction(percepts);
        }
    }
}