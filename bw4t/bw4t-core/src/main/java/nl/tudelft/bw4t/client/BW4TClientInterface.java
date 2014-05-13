package nl.tudelft.bw4t.client;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Collection;

import nl.tudelft.bw4t.map.NewMap;
import eis.exceptions.EntityException;
import eis.iilang.EnvironmentState;

/**
 * This is a listener at the client side that allows the server to do callbacks
 * to the client.
 * 
 * @author wouter
 */
public interface BW4TClientInterface extends Remote {

	/**
	 * Called by server when a new entity is available at the server.
	 * 
	 * @param entity
	 *            the id of the new entity
	 * @throws RemoteException
	 *             if an exception occurs during the execution of a remote
	 *             object call
	 * @throws EntityException
	 *             if something unexpected happens when attempting to add or
	 *             remove an entity.
	 */
	void handleNewEntity(String entity) throws RemoteException, EntityException;

	/**
	 * Called by server when an entity is freed at the server.
	 * 
	 * @param entity
	 *            the id of the free entity
	 * @param agents
	 *            the agents that need to be notified of the release of the
	 *            entity
	 * @throws RemoteException
	 *             if an exception occurs during the execution of a remote
	 *             object call
	 * 
	 */
	void handleFreeEntity(String entity, Collection<String> agents)
			throws RemoteException;

	/**
	 * Called by server when an entity is deleted at the server
	 * 
	 * @param entity
	 *            the id of the deleted entity
	 * @param agents
	 *            the agents that need to be notified of the deletion of the
	 *            entity
	 * @throws RemoteException
	 *             if an exception occurs during the execution of a remote
	 *             object call
	 * 
	 */
	void handleDeletedEntity(String entity, Collection<String> agents)
			throws RemoteException;

	/**
	 * Called when the server environment goes to a new state
	 * 
	 * @param newState
	 *            , the new state of the environment
	 * @throws RemoteException
	 *             if an exception occurs during the execution of a remote
	 *             object call
	 * 
	 */
	void handleStateChange(EnvironmentState newState) throws RemoteException;

	/**
	 * This function is called by the server to tell client about the map that
	 * is used in the simulation. This call is done before any other callback is
	 * done to the client. Server will never call this during a simulation, only
	 * one time initially at setup time.
	 * 
	 * @param theMap
	 *            is the map that is used in the server.
	 * 
	 * @throws RemoteException
	 *             if an exception occurs during the execution of a remote
	 *             object call
	 */
	void useMap(NewMap theMap) throws RemoteException;
	
	/**
	 * Newly added
	 */
	public String getType(String entity) throws EntityException, RemoteException;
}