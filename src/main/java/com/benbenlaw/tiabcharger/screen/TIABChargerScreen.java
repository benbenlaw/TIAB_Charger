package com.benbenlaw.tiabcharger.screen;

import com.benbenlaw.tiabcharger.TIABCharger;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.client.gui.screens.inventory.tooltip.DefaultTooltipPositioner;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;

import java.util.List;


public class TIABChargerScreen extends AbstractContainerScreen<TIABChargerMenu> {
    private static final Identifier TEXTURE = TIABCharger.identifier("textures/gui/charger_gui.png");
    private static final Identifier ENERGY_BAR = TIABCharger.identifier("energy_bar");

    public TIABChargerScreen(TIABChargerMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }

    @Override
    public void extractBackground(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float a) {
        super.extractBackground(guiGraphics, mouseX, mouseY, a);

        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        guiGraphics.blit(RenderPipelines.GUI_TEXTURED, TEXTURE, x, y, 0, 0, imageWidth, imageHeight, 256, 256);

        if (menu.hasEnergy()) {
            int currentEnergyHeight = menu.getEnergyFilled();
            int topOffset = 52 - currentEnergyHeight;

            guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED,ENERGY_BAR, 16, 52,0, topOffset, x + 8, y + topOffset + 16, 16, currentEnergyHeight);
        }
    }
    @Override
    public void extractRenderState(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.extractRenderState(guiGraphics, mouseX, mouseY, partialTick);

    }

    @Override
    protected void extractTooltip(GuiGraphicsExtractor graphics, int mouseX, int mouseY) {
        super.extractTooltip(graphics, mouseX, mouseY);
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        int barX = x + 8;
        int barY = y + 16;
        int barWidth = 16;
        int barHeight = 52;

        if (mouseX >= barX && mouseX <= barX + barWidth && mouseY >= barY && mouseY <= barY + barHeight) {
            int currentEnergy = menu.data.get(0);
            int maxEnergy = menu.data.get(1);

            Component text = Component.literal("Energy: "+currentEnergy+" / "+maxEnergy+" FE");
            List<ClientTooltipComponent> components = List.of(ClientTooltipComponent.create(text.getVisualOrderText()));
            graphics.tooltip(this.font, components, mouseX, mouseY, DefaultTooltipPositioner.INSTANCE, null);

        }

    }
}