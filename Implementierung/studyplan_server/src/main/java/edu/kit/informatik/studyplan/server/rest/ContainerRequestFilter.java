package edu.kit.informatik.studyplan.server.rest;

/**
 * Eine Erweiterungsschnittstelle, die von serverseitigen Anfrage-Filtern implementiert wird. 
 */
public interface ContainerRequestFilter {

	/**
	 * Filter-Methode wird aufgerufen, bevor eine Anfrage an eine Ressource versandt wird. 
	 */
	public void filter();
}
