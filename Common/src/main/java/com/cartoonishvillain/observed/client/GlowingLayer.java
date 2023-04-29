package com.cartoonishvillain.observed.client;

import com.cartoonishvillain.observed.entity.ObserverEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;

public class GlowingLayer extends RenderLayer<ObserverEntity, ObserverModel<ObserverEntity>> {
    protected final static ResourceLocation TEXTURE = new ResourceLocation("observed", "textures/entity/eyelayer.png");

    public GlowingLayer(RenderLayerParent<ObserverEntity, ObserverModel<ObserverEntity>> entityRendererIn) {
        super(entityRendererIn);
    }



    @Override
    public void render(PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn, ObserverEntity entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if(!entitylivingbaseIn.isInvisible()){
            renderColoredCutoutModel(this.getParentModel(), TEXTURE, matrixStackIn, bufferIn, 200, entitylivingbaseIn, 1.0F, 1.0F, 1.0F);
        }
    }
}
