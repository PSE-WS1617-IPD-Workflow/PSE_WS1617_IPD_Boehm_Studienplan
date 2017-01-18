package edu.kit.informatik.studyplan.server.rest;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;

/**
 * Hilfsklasse um Ressource Klassen festzulegen.
 */
@ApplicationPath("rest")
public class MainApplication extends ResourceConfig {

	/**
	 * Constructor for config
	 */
	public MainApplication() {
		packages("edu.kit.informatik.studyplan.server.rest");
	}
};