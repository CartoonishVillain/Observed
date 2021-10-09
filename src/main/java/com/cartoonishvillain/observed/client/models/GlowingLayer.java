package com.cartoonishvillain.observed.client.models;

import com.cartoonishvillain.observed.Observed;
import com.cartoonishvillain.observed.entity.ObserverEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.util.ResourceLocation;

public class GlowingLayer extends LayerRenderer<ObserverEntity, BipedModel<ObserverEntity>> {
    protected final static ResourceLocation TEXTURE = new ResourceLocation(Observed.MODID, "textures/entity/eyelayer.png");

    public GlowingLayer(IEntityRenderer<ObserverEntity, BipedModel<ObserverEntity>> entityRendererIn) {
        super(entityRendererIn);
    }



    @Override
    public void render(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn, ObserverEntity entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if(!entitylivingbaseIn.isInvisible()){
            renderColoredCutoutModel(this.getParentModel(), TEXTURE, matrixStackIn, bufferIn, 200, entitylivingbaseIn, 1.0F, 1.0F, 1.0F);
        }
    }
}
