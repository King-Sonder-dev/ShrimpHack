package me.alpha432.oyvey.event.impl;

/**
 * This class represents an event.
 *
 * @param <T> The type of the listener.
 */
public abstract class AbstractEvent<T> {

    /**
     * Calls the listener.
     *
     * @param listener The listener to call.
     */
    public abstract void call(final T listener);

}