package net.seeals;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.minecraft.client.render.RenderLayer;
import net.seeals.item.artifact.ModArtifactBuiltinItemRenderer;
import net.seeals.item.artifact.ModArtifactItem;
import net.seeals.item.artifact.ModArtifactItemModel;
import net.seeals.item.artifact.ModArtifactItemRenderer;
import net.seeals.registry.ModBlocks;
import net.seeals.registry.ModItems;
import net.seeals.registry.ModKeyBindings;

public class BackHomeClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ModKeyBindings.registerKeyBindings();

        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.BRAZIER, RenderLayer.getCutout());

        ModArtifactItem.registerEventListeners();

        ModArtifactItemRenderer renderer = new ModArtifactItemRenderer(new ModArtifactItemModel());
        BuiltinItemRendererRegistry.INSTANCE.register(ModItems.ARTIFACT, new ModArtifactBuiltinItemRenderer(renderer));

    }
}
