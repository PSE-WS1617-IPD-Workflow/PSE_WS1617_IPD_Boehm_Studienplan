package edu.kit.informatik.studyplan.server.rest;

import javax.ws.rs.NameBinding;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Resources or resource methods annotated with @AuthorizationNeeded
 * require authorization via valid access token
 * @author NiklasUhl
 * 
 */
@NameBinding
@Retention(RetentionPolicy.RUNTIME)
public @interface AuthorizationNeeded {

}
