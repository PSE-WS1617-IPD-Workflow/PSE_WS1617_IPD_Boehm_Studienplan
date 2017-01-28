package edu.kit.informatik.studyplan.server.rest;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.ws.rs.NameBinding;

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
