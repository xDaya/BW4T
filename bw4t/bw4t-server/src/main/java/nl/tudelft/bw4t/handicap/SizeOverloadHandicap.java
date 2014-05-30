package nl.tudelft.bw4t.handicap;

import nl.tudelft.bw4t.doors.Door;
import nl.tudelft.bw4t.robots.MoveType;
import nl.tudelft.bw4t.zone.Corridor;
import nl.tudelft.bw4t.zone.Zone;
/**
 * 
 * @author Valentine Mairet & Ruben Starmans
 *
 */
public class SizeOverloadHandicap extends AbstractHandicapFactory {
	/**
	 * true if the handicap is active.
	 */
	private boolean isActive;
	
	/**
	 * Calls the super method on p,
	 * Sets the handicap to active,
	 * Adds the handicap to the robot handicap storage.
	 * @param p HandicapInterface the SizeOverloadHandicap wraps around.
	 * @param s Size the bot is gonna be.
	 */
	public SizeOverloadHandicap(HandicapInterface p, int s) {
		super(p);
		isActive = true;
		robot.setSize(s);
		robot.getHandicapsMap().put("SizeOverload", this);
	}
	
	/**
	 * Override of the checkZoneAccess method,
	 * The robot cannot access zones anymore.
	 * @param startzone Startzone
	 * @param endzone Endzone
	 * @param door Door
	 * @return MoveType.SAME_AREA
	 */
	@Override
	public MoveType checkZoneAccess(Zone startzone, Zone endzone, Door door) {
		if (isActive && robot.getSize() >= 4) {
			if (startzone == endzone) {
				return MoveType.SAME_AREA;
			} else if (endzone instanceof Corridor) {
				if (!robot.isOneBotPerZone() || endzone.containsMeOrNothing(robot)) {
					return MoveType.ENTER_CORRIDOR;
				}
				return MoveType.HIT_OCCUPIED_ZONE;
			}
		} else {
			super.checkZoneAccess(startzone, endzone, door);
		}
		return MoveType.ENTERING_FREESPACE;
	}
	
	/**
	 * Activate the handicap.
	 */
	public void activate() {
		isActive = true;
	}
	
	/**
	 * Deactivate the handicap.
	 */
	public void deactivate() {
		isActive = false;
	}
}
