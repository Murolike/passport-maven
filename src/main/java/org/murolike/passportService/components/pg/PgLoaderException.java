package org.murolike.passportService.components.pg;

/**
 * Исключение для PgLoader
 */
public class PgLoaderException extends Exception {
    public PgLoaderException(String message) {
        super(message);
    }
}
