package aids.dev.shrimphack.event.impl;

import aids.dev.shrimphack.event.Event;
import aids.dev.shrimphack.event.Stage;

public class UpdateWalkingPlayerEvent extends Event {
    private final Stage stage;

    public UpdateWalkingPlayerEvent(Stage stage) {
        this.stage = stage;
    }

    public Stage getStage() {
        return stage;
    }
}
