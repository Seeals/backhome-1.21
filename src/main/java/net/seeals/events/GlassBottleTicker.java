package net.seeals.events;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.seeals.item.liquiddream.LiquidDreamBottle;
import net.seeals.registry.ModItems;

public class GlassBottleTicker {
    public static void tick(ItemStack stack, World world, PlayerEntity player, int slot) {
        if (!world.isClient) {
            NbtComponent component = stack.get(DataComponentTypes.CUSTOM_DATA);
            NbtCompound nbt = component != null ? component.copyNbt() : new NbtCompound();

            int progress = nbt.getInt(LiquidDreamBottle.TRANSFORMATION_PROGRESS_KEY);
            if (progress >= 1 && stack.getItem() != ModItems.PARTIAL_LIQUID_DREAM && stack.isOf(Items.GLASS_BOTTLE)) {
                // Convert water bottle to partial liquid dream
                ItemStack newStack = new ItemStack(ModItems.PARTIAL_LIQUID_DREAM);
                newStack.apply(DataComponentTypes.CUSTOM_DATA, NbtComponent.of(nbt), (current) -> NbtComponent.of(nbt));
                player.getInventory().setStack(slot, newStack);

                //TODO Fix sounds here
                // player.playSound(SoundEvents.ITEM_BOTTLE_FILL_DRAGONBREATH, 1f, 1f);
            }
        }
    }
}
