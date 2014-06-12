package nl.tudelft.bw4t.model.zone;

import java.awt.Color;

import repast.simphony.context.Context;
import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.grid.Grid;

/**
 * A room which might have block in it on initialization.
 */
public class BlocksRoom extends Room {

    /** Variables to keep track of the color cycle for this type of room */
    private static int count;
    private static Color[] cycle = { Color.YELLOW, Color.GREEN, Color.PINK };

    /**
     * Creates a new room in which block might be placed.
     * 
     * @param space
     *            The space in which the room will be located.
     * @param context
     *            The context in which the room will be located.
     */
    public BlocksRoom(ContinuousSpace<Object> space, Grid<Object> grid, Context<Object> context, nl.tudelft.bw4t.map.Zone roomzone) {
        super(cycle[count % cycle.length], roomzone, space, grid, context);
        setCount(); 
    }
    
    private static void setCount(){
    	count++; 
    }
}
