package net.seeals.item.artifact;


import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import net.seeals.BackHome;
import net.seeals.registry.ModKeyBindings;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.SingletonGeoAnimatable;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.*;
import software.bernie.geckolib.util.GeckoLibUtil;


public class ModArtifactItem extends Item implements GeoItem {
    private AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public ModArtifactItem(Settings settings) {
        super(settings);
        SingletonGeoAnimatable.registerSyncedAnimatable(this);
    }


    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        //register the animation controller
        AnimationController<ModArtifactItem> controller = new AnimationController<>(this, "controller", 20, this::animationPredicate);

        //create RawAnimation
        RawAnimation closeAnim = RawAnimation.begin().then("animation.artifact.close", Animation.LoopType.HOLD_ON_LAST_FRAME);
        RawAnimation openAnim = RawAnimation.begin().then("animation.artifact.open", Animation.LoopType.PLAY_ONCE);
        RawAnimation focusAnim = RawAnimation.begin().then("animation.artifact.focus", Animation.LoopType.HOLD_ON_LAST_FRAME);
        RawAnimation unfocusAnim = RawAnimation.begin().then("animation.artifact.unfocus", Animation.LoopType.PLAY_ONCE);

        //Register the triggerable animation
        controller.triggerableAnim("close", closeAnim);
        controller.triggerableAnim("open", openAnim);
        controller.triggerableAnim("focus", focusAnim);
        controller.triggerableAnim("unfocus", unfocusAnim);

        controllers.add(controller);
    }

    private PlayState animationPredicate(AnimationState<ModArtifactItem> modArtifactItemAnimationState) {
        return PlayState.CONTINUE;
    }


    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }



    //hold m4

    public void onClose(ItemStack stack, World world, PlayerEntity player) {
        if (!world.isClient()) {
            ServerWorld serverWorld = (ServerWorld) world;
            long instanceId = GeoItem.getOrAssignId(stack, serverWorld);
            triggerAnim(player, instanceId, "controller", "close");
            BackHome.LOGGER.info("Close animation playing!");
        }
    }

    //released m4
    public void onReleasedClose(ItemStack stack, World world, PlayerEntity player) {
        if (!world.isClient()) {
            ServerWorld serverWorld = (ServerWorld) world;
            long instanceId = GeoItem.getOrAssignId(stack, serverWorld);
            triggerAnim(player, instanceId, "controller", "open");
            BackHome.LOGGER.info("Open animation playing!");
        }
    }

    //hold m5
    public void onFocus(ItemStack stack, World world, PlayerEntity player) {
        if (!world.isClient()) {
            ServerWorld serverWorld = (ServerWorld) world;
            long instanceId = GeoItem.getOrAssignId(stack, serverWorld);
            triggerAnim(player, instanceId, "controller", "focus");
            BackHome.LOGGER.info("Focus animation playing!");
        }
    }

    //released m5
    public void onReleasedFocus(ItemStack stack, World world, PlayerEntity player) {
        if (!world.isClient()) {
            ServerWorld serverWorld = (ServerWorld) world;
            long instanceId = GeoItem.getOrAssignId(stack, serverWorld);
            triggerAnim(player, instanceId, "controller", "unfocus");
            BackHome.LOGGER.info("Unfocus animation playing!");
        }
    }
    //TODO THIS FUCKING registerEventListeners IS ON CLIENT SIDE CUS EVERYTHING IS ON CLIENT SIDE BUT THE FUCKING ANIMATION REQUIRES THE WORLD DATA TO BE ON THE SERVER SIDE AND FUUUUUUUUCK FIX IT PLEASE
    //Event listener to handle left-click actions (bro :c) Keep in mind that this only detects on the off hand but we want it to work on both hands so TODO here lol
    public static void registerEventListeners() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player != null) {
                ItemStack stack = client.player.getOffHandStack();
                if (stack.getItem() instanceof ModArtifactItem) {
                    ModArtifactItem item = (ModArtifactItem) stack.getItem();
                    boolean closeKeyPressed = ModKeyBindings.closeKey.isPressed();
                    boolean focusKeyPressed = ModKeyBindings.focusKey.isPressed();

                    if (closeKeyPressed) {
                        item.onClose(stack, client.world, client.player);
                        BackHome.LOGGER.info("Close key is pressed");
                    } else {
                        item.onReleasedClose(stack, client.world, client.player);
                        BackHome.LOGGER.info("Close key is released");
                    }
                    if (focusKeyPressed) {
                        item.onFocus(stack, client.world, client.player);
                        BackHome.LOGGER.info("Focus key is pressed");
                    } else {
                        item.onReleasedFocus(stack, client.world, client.player);
                        BackHome.LOGGER.info("Focus key is released");
                    }
                }
            }
        });
    }
}