package com.cartoonishvillain.observed.client.models;

import com.cartoonishvillain.observed.Observed;
import com.cartoonishvillain.observed.entity.ObserverEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.BipedRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.util.ResourceLocation;

public class RenderObserver extends BipedRenderer<ObserverEntity, BipedModel<ObserverEntity>> {

    protected final static ResourceLocation TEXTURE = new ResourceLocation(Observed.MODID, "textures/entity/observerflesh.png");


    public RenderObserver(EntityRendererManager p_174169_) {
        super(p_174169_, new Model(), 0.5f);
        this.addLayer(new GlowingLayer(this));
    }

    private static class Model extends BipedModel<ObserverEntity> {
        private static RenderType makeRenderType(ResourceLocation texture) {
            RenderType normal = RenderType.entityCutoutNoCull(texture);
            return normal;}

        Model() {
            super(Model::makeRenderType, 0, 0, 64, 64);
        }
    }


    @Override
    public ResourceLocation getTextureLocation(ObserverEntity entity) {
        return TEXTURE;
    }
}
