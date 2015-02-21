package com.example.lisamazzini.train_app.exceptions;


/**
 * Exception che rappresenta lo sblocco dell'Achievement del ritardo.
 *
 * @author lisamazzini
 */
public class DelayAchievementException extends AchievementException {

    /**
     * Costruttore.
     * @param message messaggio da mostrare
     */
    public DelayAchievementException(final String message) {
        super(message);
    }
}
