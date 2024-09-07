package aids.dev.shrimphack.util.discord.callbacks;

import aids.dev.shrimphack.util.discord.DiscordUser;
import com.sun.jna.Callback;

public interface JoinRequestCallback extends Callback {
    void apply(final DiscordUser p0);
}
