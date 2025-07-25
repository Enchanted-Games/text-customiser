package games.enchanted.eg_text_customiser.common.util;

import net.minecraft.client.Minecraft;
//? if minecraft: > 1.21.4 {
import net.minecraft.util.profiling.Profiler;
//?}

public class Profiling {
    public static void push(String name) {
        //? if minecraft: <= 1.21.4 {
        /*Minecraft.getInstance().getProfiler().push(name);
        *///?} else {
        Profiler.get().push("etc:" + name);
        //?}
    }

    public static void pop() {
        //? if minecraft: <= 1.21.4 {
        /*Minecraft.getInstance().getProfiler().pop();
        *///?} else {
        Profiler.get().pop();
        //?}
    }
}
