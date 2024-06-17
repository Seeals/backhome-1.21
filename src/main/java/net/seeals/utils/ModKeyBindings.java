package net.seeals.utils;

import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.seeals.BackHome;
import org.lwjgl.glfw.GLFW;

public class ModKeyBindings {
    public static KeyBinding closeKey;
    public static KeyBinding focusKey;

    public static void registerKeyBindings() {
        closeKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.backhome.close",
                InputUtil.Type.MOUSE,
                GLFW.GLFW_MOUSE_BUTTON_3,
                "category.mod.controls"
        ));
        BackHome.LOGGER.info("Right Click Key Binding Registered: " + closeKey.getTranslationKey());

        focusKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.backhome.focus",
                InputUtil.Type.MOUSE,
                GLFW.GLFW_MOUSE_BUTTON_4,
                "category.backhome.controls"
        ));
        BackHome.LOGGER.info("Left Click Key Binding Registered: " + focusKey.getTranslationKey());
    }
}
