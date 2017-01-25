package edu.kit.informatik.studyplan.server.rest;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.StatusType;

public class UnprocessableEntityException extends WebApplicationException {
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


    public UnprocessableEntityException() {
        super(STATUS_TYPE.getReasonPhrase(), STATUS_TYPE.getStatusCode());
    }
}
