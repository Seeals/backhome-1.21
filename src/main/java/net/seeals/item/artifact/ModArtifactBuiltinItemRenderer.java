package net.seeals.item.artifact;

import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRenderer;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;

public class ModArtifactBuiltinItemRenderer implements  BuiltinItemRendererRegistry. DynamicItemRenderer  {
    private final ModArtifactItemRenderer geoItemRenderer;

    public ModArtifactBuiltinItemRenderer(ModArtifactItemRenderer geoItemRenderer) {
        this.geoItemRenderer = geoItemRenderer;
    }


    @Override
    public void render(ItemStack stack, ModelTransformationMode mode, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        geoItemRenderer.render(stack, mode, matrices, vertexConsumers, light, overlay);
    }
}
