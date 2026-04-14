package com.limasantos.pharmacy.api.shared.exception;

/**
 * Exceção lançada quando uma regra de negócio é violada
 */
public class BusinessRuleException extends RuntimeException {
    
    public BusinessRuleException(String message) {
        super(message);
    }
    
    public BusinessRuleException(String message, Throwable cause) {
        super(message, cause);
    }
}

