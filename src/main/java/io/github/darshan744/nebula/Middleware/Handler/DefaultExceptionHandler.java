package io.github.darshan744.nebula.Middleware.Handler;

import java.time.LocalDateTime;

import io.github.darshan744.nebula.Http.HttpRequest.Request;
import io.github.darshan744.nebula.Http.HttpResponse.Response;
import io.github.darshan744.nebula.Logger.NebulaLogger;
import io.github.darshan744.nebula.Logger.NebulaLoggerFactory;
import io.github.darshan744.nebula.Middleware.ExceptionHandler;

/**
 * @apiNote
 *          Default Exception handler for the framework
 */
public class DefaultExceptionHandler implements ExceptionHandler {

    private static final NebulaLogger logger = NebulaLoggerFactory.getLogger(DefaultExceptionHandler.class);

    private class ErrorResponse {
        private String error;
        private String httpMethod;
        private String route;
        private String date;

        public ErrorResponse(String error, String httpMethod, String route) {
            this.error = error;
            this.httpMethod = httpMethod;
            this.route = route;
            this.date = LocalDateTime.now().toString();
        }

        public String getError() {
            return error;
        }

        public void setError(String error) {
            this.error = error;
        }

        public String getHttpMethod() {
            return httpMethod;
        }

        public void setHttpMethod(String httpMethod) {
            this.httpMethod = httpMethod;
        }

        public String getRoute() {
            return route;
        }

        public void setRoute(String route) {
            this.route = route;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

    }

    private class ParsingError {
        private String error;
        private String timeStamps;

        public ParsingError(String error, String timeStamps) {
            this.error = error;
            this.timeStamps = timeStamps;
        }

        public String getError() {
            return error;
        }

        public void setError(String error) {
            this.error = error;
        }

        public String getTimeStamps() {
            return timeStamps;
        }

        public void setTimeStamps(String timeStamps) {
            this.timeStamps = timeStamps;
        }

    }

    @Override
    public void handleException(Exception e, Request req, Response res) {
        System.out.println("DEFAULT HANDLER");
        logger.severe(e.getMessage());
        ErrorResponse response = new ErrorResponse(e.getMessage(), req.getMethod().toString(), req.getUrl());
        res.json(response);
    }
}
