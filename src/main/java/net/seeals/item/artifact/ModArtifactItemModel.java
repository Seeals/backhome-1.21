package net.seeals.item.artifact;

import net.minecraft.util.Identifier;
import net.seeals.BackHome;
import software.bernie.geckolib.model.GeoModel;

public class ModArtifactItemModel extends GeoModel<ModArtifactItem> {
    @Override
    public Identifier getModelResource(ModArtifactItem animatable) {
        return Identifier.of(BackHome.MOD_ID, "geo/artifact.geo.json");
    }

    @Override
    public Identifier getTextureResource(ModArtifactItem animatable) {
        return Identifier.of(BackHome.MOD_ID, "textures/item/artifact.png");
    }

    @Override
    public Identifier getAnimationResource(ModArtifactItem animatable) {
        return Identifier.of(BackHome.MOD_ID, "animations/artifact.animation.json");
    }
}
