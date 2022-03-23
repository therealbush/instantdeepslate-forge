package me.bush.instantdeepslate.tweak;

import me.bush.instantdeepslate.InstantDeepslate;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.text.TranslatableText;

import java.lang.reflect.Field;

public class BlockTweak {
    // The names for the fields that are being changed. It may be useful to change these if you are using different mappings.
    private static final String settings = "field_23155";
    // There are two fields in different places for explosion resistance, this is for the one in BlockBehaviour.class
    private static final String altResistance = "field_23160";
    // This is for the field in BlockBehaviour.Properties.class
    private static final String resistance = "field_10660";
    // The destroy time field in BlockBehaviour
    private static final String hardness = "field_10669";
    // The default blockstate field in Block
    private static final String defaultState = "field_10646";
    // The destroy speed field in BlockStateBase
    private static final String altHardness = "field_23172";

    private final Block block;
    private float[] defaultBehavior;
    private float[] targetBehavior;

    public BlockTweak(Block block, Block target) {
        try {
            this.defaultBehavior = this.getBlockProperties(block);
        } catch (Exception exception) {
            InstantDeepslate.logger.error("Could not get block properties for " + this.getName(block), exception);
        }
        try {
            this.targetBehavior = this.getBlockProperties(target);
        } catch (Exception exception) {
            InstantDeepslate.logger.error("Could not get block properties for " + this.getName(target), exception);
        }
        this.block = block;
    }

    public void enable() {
        if (this.targetBehavior == null) return;
        try {
            this.setBlockProperties(this.block, this.targetBehavior);
        } catch (Exception exception) {
            InstantDeepslate.logger.error("Could not change block properties for " + this.getName(this.block), exception);
        }
    }

    public void disable() {
        if (this.defaultBehavior == null) return;
        try {
            this.setBlockProperties(this.block, this.defaultBehavior);
        } catch (Exception exception) {
            InstantDeepslate.logger.error("Could not revert block properties for " + this.getName(this.block), exception);
        }
    }

    private float[] getBlockProperties(Block block) throws Exception {
        // Get block settings field in BlockBehaviour
        Field settingsField = AbstractBlock.class.getDeclaredField(BlockTweak.settings);
        settingsField.setAccessible(true);
        AbstractBlock.Settings settings = (AbstractBlock.Settings) settingsField.get(block);
        // Get block state field in Block
        Field defaultStateField = Block.class.getDeclaredField(BlockTweak.defaultState);
        defaultStateField.setAccessible(true);
        BlockState blockState = (BlockState) defaultStateField.get(block);
        // Get fields
        Field altResistanceField = AbstractBlock.class.getDeclaredField(BlockTweak.altResistance);
        Field resistanceField = AbstractBlock.Settings.class.getDeclaredField(BlockTweak.resistance);
        Field hardnessField = AbstractBlock.Settings.class.getDeclaredField(BlockTweak.hardness);
        Field altHardnessField = AbstractBlock.AbstractBlockState.class.getDeclaredField(BlockTweak.altHardness);
        // Force accessible
        altResistanceField.setAccessible(true);
        resistanceField.setAccessible(true);
        hardnessField.setAccessible(true);
        altHardnessField.setAccessible(true);
        // Get values
        float altExplosionResistance = altResistanceField.getFloat(block);
        float explosionResistance = resistanceField.getFloat(settings);
        float destroyTime = hardnessField.getFloat(settings);
        float destroySpeed = altHardnessField.getFloat(blockState);
        // Return explosion resistance 1 & 2, and destroy time
        return new float[]{altExplosionResistance, explosionResistance, destroyTime, destroySpeed};
    }

    private void setBlockProperties(Block block, float[] targetProperties) throws Exception {
        // Get block settings field
        Field settingsField = AbstractBlock.class.getDeclaredField(BlockTweak.settings);
        settingsField.setAccessible(true);
        AbstractBlock.Settings settings = (AbstractBlock.Settings) settingsField.get(block);
        // Get block state field in Block
        Field defaultStateField = Block.class.getDeclaredField(BlockTweak.defaultState);
        defaultStateField.setAccessible(true);
        BlockState defaultState = (BlockState) defaultStateField.get(block);
        // Get fields
        Field altResistanceField = AbstractBlock.class.getDeclaredField(BlockTweak.altResistance);
        Field resistanceField = AbstractBlock.Settings.class.getDeclaredField(BlockTweak.resistance);
        Field hardness = AbstractBlock.Settings.class.getDeclaredField(BlockTweak.hardness);
        Field altHardness = AbstractBlock.AbstractBlockState.class.getDeclaredField(BlockTweak.altHardness);
        // Force accessible
        altResistanceField.setAccessible(true);
        resistanceField.setAccessible(true);
        hardness.setAccessible(true);
        altHardness.setAccessible(true);
        // Set properties to target
        altResistanceField.set(block, targetProperties[0]);
        resistanceField.set(settings, targetProperties[1]);
        hardness.set(settings, targetProperties[2]);
        altHardness.set(defaultState, targetProperties[3]);
    }

    private String getName(Block block) {
        return ((TranslatableText) block.getName()).getKey();
    }
}
