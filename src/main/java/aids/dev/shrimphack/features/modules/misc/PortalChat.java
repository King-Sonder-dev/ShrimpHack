package aids.dev.shrimphack.features.modules.misc;

import aids.dev.shrimphack.features.modules.Module;
import aids.dev.shrimphack.mixin.portalchat.IEntity;

public class PortalChat extends Module {

    public PortalChat() {
        super("PortalChat", "PortalChat", Category.MISC, true, false, false);
    }

    @Override
    public void onUpdate() {
        ((IEntity)mc.player).setInNetherPortal(false);
    }
}
