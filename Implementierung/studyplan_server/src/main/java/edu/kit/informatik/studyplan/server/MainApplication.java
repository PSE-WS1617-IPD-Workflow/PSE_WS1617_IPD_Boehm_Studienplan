package edu.kit.informatik.studyplan.server;

import javax.ws.rs.ApplicationPath;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.process.internal.RequestScoped;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;

import edu.kit.informatik.studyplan.server.model.userdata.authorization.AuthorizationContext;
import edu.kit.informatik.studyplan.server.rest.AuthorizationContextFactory;
import edu.kit.informatik.studyplan.server.rest.AuthorizationRequestFilter;
import edu.kit.informatik.studyplan.server.rest.CorsResponseFilter;
import edu.kit.informatik.studyplan.server.rest.OptionsResponseFilter;
import edu.kit.informatik.studyplan.server.rest.ValidationConfigContextResolver;

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
		register(new AbstractBinder() {
			@Override
			protected void configure() {
				bindFactory(AuthorizationContextFactory.class)
						.to(AuthorizationContext.class).in(RequestScoped.class);
			}
		});
		register(ValidationConfigContextResolver.class);
		property(ServerProperties.BV_DISABLE_VALIDATE_ON_EXECUTABLE_OVERRIDE_CHECK, true);
		register(AuthorizationRequestFilter.class);
		register(OptionsResponseFilter.class);
		register(CorsResponseFilter.class);
	}
};