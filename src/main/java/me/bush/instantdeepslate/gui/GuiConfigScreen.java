package me.bush.instantdeepslate.gui;

import com.mojang.blaze3d.vertex.PoseStack;
import me.bush.instantdeepslate.InstantDeepslate;
import net.minecraft.client.gui.components.CycleButton;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;

public class GuiConfigScreen extends Screen {

    public GuiConfigScreen() {
        super(new TextComponent("InstantDeepslate"));
    }

    @Override
    protected void init() {
        CycleButton<Boolean> enabledButton = new CycleButton.Builder<Boolean>(enabled -> new TranslatableComponent(enabled ? "On" : "Off"))
                .withInitialValue(InstantDeepslate.tweakRegistry.enabled).withValues(true, false)
                .create(this.width / 2 - 50, 50, 100, 20, new TranslatableComponent("Instant"), (button, value) -> InstantDeepslate.tweakRegistry.enableTweaks(value));
        this.addRenderableWidget(enabledButton);
        super.init();
    }

    @Override
    public void render(PoseStack stack, int x, int y, float delta) {
        this.renderBackground(stack);
        drawCenteredString(stack, this.font, "InstantDeepslate Config", this.width / 2, 20, 0xFFFFFF);
        super.render(stack, x, y, delta);
    }
}
