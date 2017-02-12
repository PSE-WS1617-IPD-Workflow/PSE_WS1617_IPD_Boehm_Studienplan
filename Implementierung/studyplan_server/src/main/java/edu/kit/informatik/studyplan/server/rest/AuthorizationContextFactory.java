package edu.kit.informatik.studyplan.server.rest;

import edu.kit.informatik.studyplan.server.model.userdata.dao.AuthorizationContext;
import org.glassfish.hk2.api.Factory;

import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;

/**
 * Factory for injection of AuthorizationContext instances into the REST resource classes.
 */
public class AuthorizationContextFactory implements Factory<AuthorizationContext> {
    private static final String AUTHORIZATION_CONTEXT_PROPERTY = "authorizationContext";
    private final ContainerRequestContext context;

    /**
     * Only being called by Jersey. Creates a new factory.
     * @param context a given context.
     */
    @Inject
    public AuthorizationContextFactory(ContainerRequestContext context) {
        this.context = context;
    }

    /**
     * Only being called by Jersey. Retrieves an AuthorizationContext from a ContainerRequestContext's Property set
     * before.
     * @return the context.
     */
    @Override
    public AuthorizationContext provide() {
        return (AuthorizationContext) context.getProperty(AUTHORIZATION_CONTEXT_PROPERTY);
    }

    /**
     * Does nothing.
     * @param authorizationContext .
     */
    @Override
    public void dispose(AuthorizationContext authorizationContext) { }

    /**
     * Sets the requestContext's "authorizationContext" property to a given AuthorizationContext.
     * To access the AuthorizationContext inside your REST resource class, use
     * <pre><code>
     *    {@literal @}Inject
     *     Provider&lt;AuthorizationContext&gt; context;
     * </code></pre>
     * @param requestContext the requestContext whose property shall be set
     * @param authorizationContext the authorizationContext to assign
     */
    static void setContext(ContainerRequestContext requestContext, AuthorizationContext authorizationContext) {
        requestContext.setProperty(AUTHORIZATION_CONTEXT_PROPERTY, authorizationContext);
    }
}
