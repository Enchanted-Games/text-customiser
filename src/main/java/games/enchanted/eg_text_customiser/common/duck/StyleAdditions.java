package games.enchanted.eg_text_customiser.common.duck;

import net.minecraft.network.chat.Style;

public interface StyleAdditions {
    Style eg_text_customiser$resetShadowColour();

    /**
     * If true, the shadow colour has not been modified by a colour override and should be inherited as normal
     * If false, the shadow colour should not be inherited as it has been modified by a colour override
     *
     * @return the boolean
     */
    boolean eg_text_customiser$shouldInheritShadowColour();
    void eg_text_customiser$setInheritShadowColour(boolean newInheritance);

    boolean eg_text_customiser$hasBeenOverridden();
    void eg_text_customiser$setHasBeenOverridden(boolean newValue);
}
