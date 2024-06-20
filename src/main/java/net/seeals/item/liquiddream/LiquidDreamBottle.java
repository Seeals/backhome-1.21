package net.seeals.item.liquiddream;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.seeals.BackHome;
import net.seeals.registry.ModItems;
import net.seeals.utils.ItemUtils;

import java.util.List;

//TODO in future when we got better at modding: Maybe make it so its a singular item with different NBT tags
// so we could implement different progresses of the item and assign each state to each compounds. If we're fancy,
// we could also add progress bar (durability bar) for the bottle itself to track progress.
// We could also find some custom sounds too if we want.

//TODO TO FIX, make it so that the bottle that the player is holding while they went to sleep will be filled.
// Right now, the entire inventory of glass bottle will be filled. And when the glass bottles are stacked,
// all of them will be deleted, replacing with only 1 partial liquid dream.


public class LiquidDreamBottle extends Item {
    public LiquidDreamBottle(Settings settings) {
        super(settings);
    }

    public static final String TRANSFORMATION_PROGRESS_KEY = "transformation_progress";

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (!world.isClient && entity instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) entity;
            NbtComponent component = stack.get(DataComponentTypes.CUSTOM_DATA);
            NbtCompound nbt = component !=null ? component.copyNbt() : new NbtCompound();

            // Log current progress for debugging
            int progress1 = nbt.getInt(TRANSFORMATION_PROGRESS_KEY);
            BackHome.LOGGER.info("Current progress (before tick): " + progress1);

            // ticking logic, check for glass bottle or partial liquid dream to have it at atleast 1 progress
            if (stack.isOf(Items.GLASS_BOTTLE) && ItemUtils.isItemInEitherHand(player) || stack.getItem() == ModItems.PARTIAL_LIQUID_DREAM && ItemUtils.isItemInEitherHand(player)) {
                int progress = nbt.getInt(TRANSFORMATION_PROGRESS_KEY);
                if (progress < 100) {
                    progress += 1;
                    stack.set(DataComponentTypes.CUSTOM_DATA, NbtComponent.of(nbt));

                    // Log new progress for debugging
                    BackHome.LOGGER.info("New progress (after tick): " + progress);

                }
                //glass bottle -> partial liquid dream already being handled in GlassBottleTicker

                if (progress >= 100 && stack.getItem() != ModItems.LIQUID_DREAM && stack.getItem() == ModItems.PARTIAL_LIQUID_DREAM) {
                    ItemStack newStack = new ItemStack(ModItems.LIQUID_DREAM);
                    newStack.set(DataComponentTypes.CUSTOM_DATA, NbtComponent.of(nbt));
                    ((PlayerEntity) entity).getInventory().setStack(slot, newStack);

                    //TODO Fix sounds here. This is playing on server side, needs to play on client side.
                    // world.playSound(null, player.getBlockPos(), SoundEvents.ITEM_BOTTLE_FILL_DRAGONBREATH, SoundCategory.PLAYERS, 2.0F, 1.0F);

                }
            }
        }
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        NbtComponent component = stack.get(DataComponentTypes.CUSTOM_DATA);
        if (component != null) {
            NbtCompound nbt = component.copyNbt();
            int progress = nbt.getInt(TRANSFORMATION_PROGRESS_KEY);
            tooltip.add(Text.literal("Dream caught progress: " + progress + "%").formatted(Formatting.GOLD));
        }
        super.appendTooltip(stack, context, tooltip, type);
    }
}
