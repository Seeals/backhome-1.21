package net.seeals.registry;

import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.seeals.BackHome;
import net.seeals.item.artifact.ModArtifactItem;
import net.seeals.item.liquiddream.LiquidDreamBottle;

public class ModItems{

    public static final Item ARTIFACT = registerItem("artifact", new ModArtifactItem( new Item.Settings()));
    public static final Item DREAM_SHARD = registerItem("dream_shard", new Item(new Item.Settings()));
    public static final Item PARTIAL_LIQUID_DREAM = registerItem("partial_liquid_dream", new LiquidDreamBottle(new Item.Settings()));
    public static final Item LIQUID_DREAM = registerItem("liquid_dream", new Item(new Item.Settings()));

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, Identifier.of(BackHome.MOD_ID, name), item);
    }

    public static void modItemInit() {
        BackHome.LOGGER.info("Registering items for " + BackHome.MOD_ID);
    }
}
