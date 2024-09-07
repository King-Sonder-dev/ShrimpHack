package aids.dev.shrimphack.event.impl;


import aids.dev.shrimphack.event.StageEvent;

public class TickEvent extends StageEvent {
    public static class Pre extends TickEvent {
        private static final Pre INSTANCE = new Pre();

        public static Pre get() {
            return INSTANCE;
        }
    }

    public static class Post extends TickEvent {
        private static final Post INSTANCE = new Post();

        public static Post get() {
            return INSTANCE;
        }
    }
}