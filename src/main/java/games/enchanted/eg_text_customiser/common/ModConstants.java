package games.enchanted.eg_text_customiser.common;

import net.minecraft.client.gui.components.toasts.SystemToast;

public class ModConstants {
    public static final String MOD_NAME = "Text Customiser";
    public static final String MOD_ID = "eg_text_customiser";

    public static final SystemToast.SystemToastId RELOAD_FAILED_TOAST = new SystemToast.SystemToastId(10000L);

    //? if fabric {
    public static final String TARGET_PLATFORM = "fabric";
    //?}
    //? if neoforge {
    /*public static final String TARGET_PLATFORM = "neoforge";
    *///?}
}
