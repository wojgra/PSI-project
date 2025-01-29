package com.stripe.sample.errorhandlers;

public class StripeExceptions {

    // API Exception
    public static class ApiException extends RuntimeException {
        private final String errorCode;
        private final int statusCode;

        public ApiException(String message, String errorCode, int statusCode) {
            super(message);
            this.errorCode = errorCode;
            this.statusCode = statusCode;
        }

        public String getErrorCode() { return errorCode; }
        public int getStatusCode() { return statusCode; }
    }

    // Bad Request Exception
    public static class BadRequestException extends ApiException {
        public BadRequestException(String message) {
            super(message, "bad_request", 400);
        }
    }

    // Resource Not Found Exception
    public static class ResourceNotFoundException extends ApiException {
        public ResourceNotFoundException(String resource, String id) {
            super(
                String.format("%s with id %s not found", resource, id),
                "resource_not_found",
                404
            );
        }
    }

    // Invalid Input Exception
    public static class InvalidInputException extends ApiException {
        public InvalidInputException(String message) {
            super(message, "invalid_input", 422);
        }
    }

    // Unauthorized Exception
    public static class UnauthorizedException extends ApiException {
        public UnauthorizedException(String message) {
            super(message, "unauthorized", 401);
        }
    }
}