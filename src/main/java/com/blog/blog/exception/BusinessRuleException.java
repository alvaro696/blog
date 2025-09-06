package com.blog.blog.exception;

public class BusinessRuleException extends RuntimeException {

    private final String ruleViolated;
    private final String details;

    public BusinessRuleException(String message) {
        super(message);
        this.ruleViolated = null;
        this.details = null;
    }

    public BusinessRuleException(String ruleViolated, String details) {
        super(String.format("Violación de regla de negocio: %s. Detalles: %s", ruleViolated, details));
        this.ruleViolated = ruleViolated;
        this.details = details;
    }

    public BusinessRuleException(String message, Throwable cause) {
        super(message, cause);
        this.ruleViolated = null;
        this.details = null;
    }

    public String getRuleViolated() {
        return ruleViolated;
    }

    public String getDetails() {
        return details;
    }
}
