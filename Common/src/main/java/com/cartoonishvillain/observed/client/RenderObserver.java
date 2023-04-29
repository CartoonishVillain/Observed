package com.cartoonishvillain.observed.client;


import com.cartoonishvillain.observed.entity.ObserverEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class RenderObserver extends MobRenderer<ObserverEntity, ObserverModel<ObserverEntity>> {

    protected final static ResourceLocation TEXTURE = new ResourceLocation("observed", "textures/entity/observer.png");

    @Override
    protected void scale(ObserverEntity $$0, PoseStack $$1, float $$2) {
        super.scale($$0, $$1, $$2);

        $$1.scale(1.5f,1.5f,1.5f);
    }

    public RenderObserver(EntityRendererProvider.Context p_174169_) {
        super(p_174169_, new ObserverModel<ObserverEntity>(p_174169_.bakeLayer(ObserverModel.LAYER_LOCATION)), 0.5f);
        this.addLayer(new GlowingLayer(this));
    }

    @Override
    public ResourceLocation getTextureLocation(ObserverEntity entity) {
        return TEXTURE;
    }
}
