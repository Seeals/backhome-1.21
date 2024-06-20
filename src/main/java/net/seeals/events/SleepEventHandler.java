package net.seeals.events;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.seeals.BackHome;
import net.seeals.item.liquiddream.LiquidDreamBottle;
import net.seeals.registry.ModItems;
import net.seeals.utils.ItemUtils;

import java.util.Random;

public class SleepEventHandler {
    private static final Random RANDOM = new Random();

    public static void onPlayerSleep(ServerPlayerEntity player, boolean wakeUp, boolean updateSleepingPlayers) {
        if (isNearDreamCatcher(player)) {
            for (ItemStack stack : player.getInventory().main) {
                //TODO TO FIX, The progress will be add to other partial dream liquid in the inventory even when player is holding a glass bottle.
                if (stack.isOf(Items.GLASS_BOTTLE) && ItemUtils.isItemInEitherHand(player) || stack.getItem() == ModItems.PARTIAL_LIQUID_DREAM && ItemUtils.isItemInEitherHand(player)) {
                    BackHome.LOGGER.info("Player is sleeping, adding progress!");

                    NbtComponent component = stack.get(DataComponentTypes.CUSTOM_DATA);
                    NbtCompound nbt = component != null ? component.copyNbt() : new NbtCompound();
                    int progress = nbt.getInt(LiquidDreamBottle.TRANSFORMATION_PROGRESS_KEY);
                    if (progress < 100) {
                        int randomIncrement = RANDOM.nextInt(20) + 1;
                        progress += randomIncrement;
                        nbt.putInt(LiquidDreamBottle.TRANSFORMATION_PROGRESS_KEY, progress + randomIncrement);

                        stack.apply(DataComponentTypes.CUSTOM_DATA, NbtComponent.of(nbt), (current) -> NbtComponent.of(nbt));

                        //this succeeds but the progress isnt saved
                        int progress1 = nbt.getInt(LiquidDreamBottle.TRANSFORMATION_PROGRESS_KEY);
                        BackHome.LOGGER.info("Current progress:" + progress1 + "%");

                        NbtComponent updatedComponent = stack.get(DataComponentTypes.CUSTOM_DATA);
                        NbtCompound updatedNbt = updatedComponent != null ? updatedComponent.copyNbt() : new NbtCompound();
                        int updatedProgress = updatedNbt.getInt(LiquidDreamBottle.TRANSFORMATION_PROGRESS_KEY);
                        BackHome.LOGGER.info("Verified progress after setting:" + updatedProgress + "%");
                    }
                }
            }
        }
    }

    //TODO add logic for isNearDreamCatcher(player) and also the Dream Catcher itself.
    private static boolean isNearDreamCatcher(ServerPlayerEntity player) {
        return true;
    }
}
