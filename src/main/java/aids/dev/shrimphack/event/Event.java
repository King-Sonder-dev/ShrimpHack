package aids.dev.shrimphack.event;

public class Event {
    private boolean cancelled;

    public boolean isCancelled() {
        return cancelled;
    }

    public void cancel() {
        cancelled = true;
    }


    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }



}
