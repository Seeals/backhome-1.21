package net.seeals.item;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ModArtifactItem extends Item {
    public ModArtifactItem(Settings settings) {
        super(settings);
    }

    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        BlockPos pos = context.getBlockPos();
        BlockState state = world.getBlockState(pos);

        if (state.getBlock() == Blocks.CAMPFIRE) {
            if (!world.isClient) {
                world.setBlockState(pos, Blocks.CACTUS.getDefaultState());
            } else {
                for (int i = 0; i < 200; i++) {
                    double offsetX = (world.random.nextDouble() - 0.5) * 2.0;
                    double offsetY = (world.random.nextDouble() - 0.5) * 2.0;
                    double offsetZ = (world.random.nextDouble() - 0.5) * 2.0;
                    world.addParticle(ParticleTypes.HEART,
                            pos.getX() + 0.5 + offsetX,
                            pos.getY() + 1.0 + offsetY,
                            pos.getZ() + 0.5 + offsetZ,
                            0.0, 0.0, 0.0);
                }
            }
            return ActionResult.SUCCESS;
        }

        return ActionResult.SUCCESS;
    }
}
