package net.seeals.events;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.entity.boss.dragon.phase.Phase;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.seeals.item.liquiddream.LiquidDreamBottle;
import net.seeals.registry.ModItems;
import net.seeals.utils.ItemUtils;

import java.util.HashSet;
import java.util.Set;

public class GlassBottleTicker {
    //TODO TOFIX idk fix the thing the processedPlayers keeps clearing cus the glass bottle have value after sleeping so
    // it just spams change the glass bottle into liquid dream fuck sake i might have to rewrite everything here.
    // But if so, we could easier make it so that we fuck the glass bottle and water bottle and make a custom non-stackable item ourself
    private static final Set<PlayerEntity> processedPlayers = new HashSet<>();

    public static void tick(ItemStack stack, World world, PlayerEntity player, int slot) {
        if (!world.isClient) {
            NbtComponent component = stack.get(DataComponentTypes.CUSTOM_DATA);
            NbtCompound nbt = component != null ? component.copyNbt() : new NbtCompound();

            int progress = nbt.getInt(LiquidDreamBottle.TRANSFORMATION_PROGRESS_KEY);
            if (progress >= 1
                    && stack.getItem() != ModItems.PARTIAL_LIQUID_DREAM
                    && stack.isOf(Items.GLASS_BOTTLE)
                    && stack.getCount() == 1
                    && ItemUtils.isItemInEitherHand(player)) {
                // Convert water bottle to partial liquid dream
                ItemStack newStack = new ItemStack(ModItems.PARTIAL_LIQUID_DREAM);
                newStack.apply(DataComponentTypes.CUSTOM_DATA, NbtComponent.of(nbt), (current) -> NbtComponent.of(nbt));

                player.getInventory().setStack(slot, newStack);

                //TODO Fix sounds here. This is playing on server side, needs to play on client side.
                // player.playSound(SoundEvents.ITEM_BOTTLE_FILL_DRAGONBREATH, 1f, 1f);

            } else if (progress >= 1
                    && stack.getItem() != ModItems.PARTIAL_LIQUID_DREAM
                    && stack.isOf(Items.GLASS_BOTTLE)
                    && stack.getCount() > 1
                    && ItemUtils.isItemInEitherHand(player)
                    && !processedPlayers.contains(player)) {

                stack.decrement(1);
                ItemStack newStack = new ItemStack(ModItems.PARTIAL_LIQUID_DREAM);
                newStack.apply(DataComponentTypes.CUSTOM_DATA, NbtComponent.of(nbt), (current) -> NbtComponent.of(nbt));

                boolean addedToInventory = player.getInventory().insertStack(newStack);

                if (!addedToInventory) {
                    player.dropItem(newStack, false);
                }

                processedPlayers.add(player);
            }
        }

    }

    public static void registerTickEvents() {
        ServerTickEvents.END_SERVER_TICK.register(GlassBottleTicker::onEndWorldTick);
    }

    private static void onEndWorldTick(MinecraftServer server) {
        resetProcessingFlag();
    }

    private static void resetProcessingFlag() {
        processedPlayers.clear();
    }



}
