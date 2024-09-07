package aids.dev.shrimphack.event.impl.autoarmor;

import aids.dev.shrimphack.event.Event;
import aids.dev.shrimphack.event.Stage;

public class PlayerMotionUpdate extends Event {
    private final Stage stage;
    public PlayerMotionUpdate(Stage stage)
    {
        this.stage = stage;
        //super(p_Era);
    }
    public Stage getStage() {
        return stage;
    }
}