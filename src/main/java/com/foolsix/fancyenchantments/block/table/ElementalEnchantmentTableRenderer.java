package com.foolsix.fancyenchantments.block.table;

import com.foolsix.fancyenchantments.FancyEnchantments;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.model.BookModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class ElementalEnchantmentTableRenderer implements BlockEntityRenderer<ElementalEnchantmentTableBlockEntity> {
    public static final ResourceLocation BOOK_TEXTURE = new ResourceLocation(FancyEnchantments.MODID, "textures/entity/elemental_enchanting_table_book.png");

    private final BookModel bookModel;

    public ElementalEnchantmentTableRenderer(BlockEntityRendererProvider.Context context) {
        this.bookModel = new BookModel(context.bakeLayer(ModelLayers.BOOK));
    }

    @Override
    public void render(ElementalEnchantmentTableBlockEntity blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay) {
        poseStack.pushPose();
        poseStack.translate(0.5F, 0.75F, 0.5F);
        float time = blockEntity.time + partialTick;
        poseStack.translate(0.0F, 0.1F + Mth.sin(time * 0.1F) * 0.01F, 0.0F);

        float rot = blockEntity.rot - blockEntity.oRot;
        while (rot >= (float) Math.PI) {
            rot -= ((float) Math.PI * 2F);
        }
        while (rot < -(float) Math.PI) {
            rot += ((float) Math.PI * 2F);
        }

        float interpolatedRot = blockEntity.oRot + rot * partialTick;
        poseStack.mulPose(Axis.YP.rotation(-interpolatedRot));
        poseStack.mulPose(Axis.ZP.rotationDegrees(80.0F));

        float flip = Mth.lerp(partialTick, blockEntity.oFlip, blockEntity.flip);
        float leftFlip = Mth.clamp(Mth.frac(flip + 0.25F) * 1.6F - 0.3F, 0.0F, 1.0F);
        float rightFlip = Mth.clamp(Mth.frac(flip + 0.75F) * 1.6F - 0.3F, 0.0F, 1.0F);
        float open = Mth.lerp(partialTick, blockEntity.oOpen, blockEntity.open);
        this.bookModel.setupAnim(time, leftFlip, rightFlip, open);

        VertexConsumer vertexConsumer = buffer.getBuffer(RenderType.entitySolid(BOOK_TEXTURE));
        this.bookModel.render(poseStack, vertexConsumer, packedLight, packedOverlay, 1.0F, 1.0F, 1.0F, 1.0F);
        poseStack.popPose();
    }
}
