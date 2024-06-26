package net.tracen.umapyoi.client;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import net.tracen.umapyoi.Umapyoi;
import net.tracen.umapyoi.UmapyoiConfig;
import net.tracen.umapyoi.api.UmapyoiAPI;
import net.tracen.umapyoi.utils.UmaSoulUtils;

public class MotivationOverlay implements IGuiOverlay {
    public static final MotivationOverlay INSTANCE = new MotivationOverlay();
    private final Minecraft minecraft = Minecraft.getInstance();

    private MotivationOverlay() {
    }

    private static final ResourceLocation HUD = new ResourceLocation(Umapyoi.MODID, "textures/gui/motivations.png");

    @Override
    public void render(ForgeGui gui, PoseStack poseStack, float partialTick, int width, int height) {
        if (!UmapyoiConfig.OVERLAY_SWITCH.get())
            return;
        int x = width / 2;
        int y = height;

        Player player = minecraft.player;
        if (player.isSpectator())
            return;

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, HUD);
        if (!UmapyoiAPI.getUmaSoul(player).isEmpty()) {
            switch (UmaSoulUtils.getMotivation(UmapyoiAPI.getUmaSoul(player))) {
            case BAD -> {
                GuiComponent.blit(poseStack, x + 118, y - 37, 0, 60, 64, 14, 64, 96);
                this.minecraft.font.draw(poseStack, Component.translatable("umapyoi.motivation.bad"), x + 132,
                        y - 34, 0XFFFFFF);
            }
            case DOWN -> {
                GuiComponent.blit(poseStack, x + 118, y - 37, 0, 45, 64, 14, 64, 96);
                this.minecraft.font.draw(poseStack, Component.translatable("umapyoi.motivation.down"), x + 132,
                        y - 34, 0XFFFFFF);
            }
            case NORMAL -> {
                GuiComponent.blit(poseStack, x + 118, y - 37, 0, 30, 64, 14, 64, 96);
                this.minecraft.font.draw(poseStack, Component.translatable("umapyoi.motivation.normal"), x + 132,
                        y - 34, 0XFFFFFF);
            }
            case GOOD -> {
                GuiComponent.blit(poseStack, x + 118, y - 37, 0, 15, 64, 14, 64, 96);
                this.minecraft.font.draw(poseStack, Component.translatable("umapyoi.motivation.good"), x + 132,
                        y - 34, 0XFFFFFF);
            }

            case PERFECT -> {
                GuiComponent.blit(poseStack, x + 118, y - 37, 0, 0, 64, 14, 64, 96);
                this.minecraft.font.draw(poseStack, Component.translatable("umapyoi.motivation.perfect"), x + 132,
                        y - 34, 0XFFFFFF);
            }
            default -> throw new IllegalArgumentException(
                    "Unexpected value: " + UmaSoulUtils.getMotivation(UmapyoiAPI.getUmaSoul(player)));
            }

        }
    }

}
