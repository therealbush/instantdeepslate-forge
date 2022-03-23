package me.bush.instantdeepslate;

import me.bush.instantdeepslate.tweak.TweakRegistry;
import net.fabricmc.api.ModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class InstantDeepslate implements ModInitializer {
    public static final Logger logger = LogManager.getLogger("InstantDeepslate");
    public static TweakRegistry tweakRegistry;

    @Override
    public void onInitialize() {
        tweakRegistry = new TweakRegistry();
        tweakRegistry.enableTweaks(true);
    }
}
