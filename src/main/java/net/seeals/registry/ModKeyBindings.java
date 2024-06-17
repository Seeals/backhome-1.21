package net.seeals.registry;

import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class ModKeyBindings {
    public static KeyBinding closeKey;
    public static KeyBinding focusKey;

    public static void registerKeyBindings() {
        closeKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.backhome.close",
                InputUtil.Type.MOUSE,
                GLFW.GLFW_MOUSE_BUTTON_4,
                "category.backhome.controls"
        ));

        focusKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.backhome.focus",
                InputUtil.Type.MOUSE,
                GLFW.GLFW_MOUSE_BUTTON_5,
                "category.backhome.controls"
        ));
    }
}
