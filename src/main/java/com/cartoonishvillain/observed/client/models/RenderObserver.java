package com.cartoonishvillain.observed.client.models;

import com.cartoonishvillain.observed.Observed;
import com.cartoonishvillain.observed.entity.ObserverEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.resources.ResourceLocation;

public class RenderObserver extends HumanoidMobRenderer<ObserverEntity, HumanoidModel<ObserverEntity>> {

    protected final static ResourceLocation TEXTURE = new ResourceLocation(Observed.MODID, "textures/entity/observerflesh.png");


    public RenderObserver(EntityRendererProvider.Context p_174169_) {
        super(p_174169_, new HumanoidModel<ObserverEntity>(p_174169_.bakeLayer(ModelLayers.PLAYER)), 0.5f);
        this.addLayer(new GlowingLayer(this));
    }

    @Override
    public ResourceLocation getTextureLocation(ObserverEntity entity) {
        return TEXTURE;
    }
}
