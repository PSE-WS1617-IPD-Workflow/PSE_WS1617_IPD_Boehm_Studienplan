package edu.kit.informatik.studyplan.server.rest;

import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;

import org.glassfish.hk2.api.Factory;

import edu.kit.informatik.studyplan.server.model.userdata.dao.AuthorizationContext;

public class AuthorizationContextFactory implements Factory<AuthorizationContext> {
    private static final String AUTHORIZATION_CONTEXT_PROPERTY = "authorizationContext";
    private final ContainerRequestContext context;

    @Inject
    public AuthorizationContextFactory(ContainerRequestContext context) {
        this.context = context;
    }

    @Override
    public AuthorizationContext provide() {
        return (AuthorizationContext) context.getProperty(AUTHORIZATION_CONTEXT_PROPERTY);
    }

    @Override
    public void dispose(AuthorizationContext authorizationContext) {
    
    }

    /**
     * Sets the requestContext's "authorizationContext" property to a given AuthorizationContext.
     * To access the AuthorizationContext inside your REST resource class, use
     * <pre><code>
     *    {@literal @}Inject
     *     AuthorizationContext context;
     * </code></pre>
     * @param requestContext the requestContext whose property shall be set
     * @param authorizationContext the authorizationContext to assign
     */
    public static void setContext(ContainerRequestContext requestContext, AuthorizationContext authorizationContext) {
        requestContext.setProperty(AUTHORIZATION_CONTEXT_PROPERTY, authorizationContext);
    }
}
