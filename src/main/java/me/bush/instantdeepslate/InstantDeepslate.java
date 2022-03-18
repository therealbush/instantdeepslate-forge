package me.bush.instantdeepslate;

import me.bush.instantdeepslate.gui.GuiConfigScreen;
import me.bush.instantdeepslate.tweak.TweakRegistry;
import net.minecraftforge.client.ConfigGuiHandler;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod("instantdeepslate")
public class InstantDeepslate {
    public static Logger logger;
    public static TweakRegistry tweakRegistry;

    public InstantDeepslate() {
        logger = LogManager.getLogger("InstantDeepslate");
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::init);
    }

    private void init(final FMLClientSetupEvent event) {
        tweakRegistry = new TweakRegistry();
        tweakRegistry.enableTweaks(true);
        ModLoadingContext.get().registerExtensionPoint(ConfigGuiHandler.ConfigGuiFactory.class,
                () -> new ConfigGuiHandler.ConfigGuiFactory((minecraft, screen) -> new GuiConfigScreen()));
    }
}
