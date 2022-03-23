package me.bush.instantdeepslate.gui;

import me.bush.instantdeepslate.InstantDeepslate;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.CyclingButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.TranslatableText;

public class ConfigGuiScreen extends Screen {

    public ConfigGuiScreen() {
        super(new TranslatableText("InstantDeepslate"));
    }

    @Override
    protected void init() {
        CyclingButtonWidget<Boolean> enabledButton = new CyclingButtonWidget.Builder<Boolean>(enabled -> new TranslatableText(enabled ? "On" : "Off"))
                .initially(InstantDeepslate.tweakRegistry.enabled).values(true, false)
                .build(this.width / 2 - 50, 50, 100, 20, new TranslatableText("Enabled"), (button, value) -> InstantDeepslate.tweakRegistry.enableTweaks(value));
        this.addDrawableChild(enabledButton);
        super.init();
    }

    @Override
    public void render(MatrixStack stack, int mouseX, int mouseY, float delta) {
        this.renderBackground(stack);
        drawCenteredText(stack, this.textRenderer, "InstantDeepslate Config", this.width / 2, 20, 0xFFFFFF);
        super.render(stack, mouseX, mouseY, delta);
    }
}
