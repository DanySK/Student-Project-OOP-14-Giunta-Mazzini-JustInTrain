package com.example.lisamazzini.train_app.exceptions;

/**
 * Exception che rappresenta lo sblocco dell'Achievement sui pin.
 *
 * @author lisamazzini
 */
public class PinAchievementException extends AchievementException {

    /**
     * Costruttore.
     * @param message messaggio da mostrare
     */
    public PinAchievementException(final String message) {
        super(message);
    }
}
