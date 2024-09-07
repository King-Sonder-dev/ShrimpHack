package aids.dev.shrimphack.event;

/**
 * @author linus
 * @see Event
 * @see Stage
 * @since 1.0
 */
public class StageEvent extends Event {
    // The current event stage which determines which segment of the event is
    // currently running.
    private Stage stage;

    /**
     * Returns the current {@link Stage} of the {@link Event}.
     *
     * @return The current event stage
     */
    public Stage getStage() {
        return stage;
    }

    /**
     * @param stage
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }
}