package net.seeals.registry;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.seeals.BackHome;

public class ModItemGroups {
    public static final ItemGroup ITEM_GROUP = Registry.register(Registries.ITEM_GROUP,
            Identifier.of(BackHome.MOD_ID, "alchemyst_item_group"),
            FabricItemGroup.builder().displayName(Text.translatable("itemgroup.items"))
                    .icon(() -> new ItemStack(Items.BUCKET)).entries((displayContext, entries) -> {
                        entries.add(ModItems.ARTIFACT);
                        entries.add(ModBlocks.BRAZIER);

                    }).build());


    public static void modItemGroupsInit() {
    }
}
