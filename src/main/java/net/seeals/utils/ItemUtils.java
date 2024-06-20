package net.seeals.utils;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;

public class ItemUtils {

    public static ItemStack getItemInEitherHand(PlayerEntity player) {
        ItemStack mainHandStack = player.getMainHandStack();
        ItemStack offHandStack = player.getOffHandStack();

        if (!mainHandStack.isEmpty()) {
            return mainHandStack;
        } else if (!offHandStack.isEmpty()) {
            return offHandStack;
        } else {
            return ItemStack.EMPTY;
        }

    }

    public static boolean isItemInEitherHand(PlayerEntity player) {
        ItemStack mainHandStack = player.getMainHandStack();
        ItemStack offHandStack = player.getOffHandStack();

        return !mainHandStack.isEmpty() || !offHandStack.isEmpty();
    }




//    public static boolean isWaterBottle(ItemStack stack) {
//        if (stack.isOf(Items.POTION)) {
//            NbtComponent component = stack.get(DataComponentTypes.CUSTOM_DATA);
//            NbtCompound tag = component !=null ? component.copyNbt() : new NbtCompound();
//            if (tag != null && tag.contains("Potion")) {
//                return "minecraft:water".equals(tag.getString("Potion"));
//            }
//        }
//        return false;
//    }


}
