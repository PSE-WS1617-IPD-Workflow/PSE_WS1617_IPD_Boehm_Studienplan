package edu.kit.informatik.studyplan.server.rest;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.StatusType;

/**
 * Exception class for 422 Unprocessable Entity response.
 */
public class UnprocessableEntityException extends WebApplicationException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 8549728633437465089L;
	/**
     * Status type of the exception.
     */
    public static final StatusType STATUS_TYPE = new StatusType() {
        @Override
        public int getStatusCode() {
            return 422;
        }

        @Override
        public Response.Status.Family getFamily() {
            return Response.Status.Family.CLIENT_ERROR;
        }

        @Override
        public String getReasonPhrase() {
            return "Unprocessable Entity";
        }
    };

    /**
     * Creates a new UnprocessableEntityException.
     */
    public UnprocessableEntityException() {
        super(STATUS_TYPE.getReasonPhrase(), STATUS_TYPE.getStatusCode());
    }
}
