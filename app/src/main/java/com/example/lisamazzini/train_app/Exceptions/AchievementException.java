package com.example.lisamazzini.train_app.exceptions;

/**
 * Exception che rappresenta lo sblocco di un Achievement.
 *
 * @author lisamazzini
 */
public class AchievementException extends Exception {

    /**
     * Costruttore.
     * @param message messaggio da mostrare
     */
    public AchievementException(final String message) {
        super(message);
    }
}
