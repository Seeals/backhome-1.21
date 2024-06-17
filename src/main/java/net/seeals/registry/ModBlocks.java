package net.seeals.registry;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.seeals.BackHome;

public class ModBlocks {

    public static final Block BRAZIER = registerBlock("brazier",
            new Block(AbstractBlock.Settings.copy(Blocks.IRON_BARS).strength(0.5f).nonOpaque()));

    private static Block registerBlock(String name, Block block) {
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, Identifier.of(BackHome.MOD_ID, name), block);
    }

    private static Item registerBlockItem(String name, Block block) {
        return Registry.register(Registries.ITEM, Identifier.of(BackHome.MOD_ID, name), new BlockItem(block, new Item.Settings()));
    }

    public static void modBlocksInit() {
    }
}
