package me.bush.instantdeepslate.tweak;

import me.bush.instantdeepslate.InstantDeepslate;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

import java.lang.reflect.Field;

public class BlockTweak {
    // The names for the fields that are being changed. It may be useful to change these if you are using different mappings.
    private static final String properties = "aP";
    // There are two fields in different places for explosion resistance, this is for the one in BlockBehaviour.class
    private static final String altExplosionResistance = "aI";
    // This is for the field in BlockBehaviour.Properties.class
    private static final String explosionResistance = "f";
    // The destroy time field in BlockBehaviour
    private static final String destroyTime = "g";
    // The default blockstate field in Block
    private static final String defaultBlockState = "b";
    // The destroy speed field in BlockStateBase
    private static final String destroySpeed = "k";

    private final Block block;
    private float[] defaultBehavior;
    private float[] targetBehavior;

    public BlockTweak(Block block, Block target) {
        try {
            this.defaultBehavior = this.getBlockProperties(block);
        } catch (Exception exception) {
            InstantDeepslate.logger.error("Could not get block properties for " + block.getDescriptionId(), exception);
        }
        try {
            this.targetBehavior = this.getBlockProperties(target);
        } catch (Exception exception) {
            InstantDeepslate.logger.error("Could not get block properties for " + target.getDescriptionId(), exception);
        }
        this.block = block;
    }

    public void enable() {
        if (this.targetBehavior == null) return;
        try {
            this.setBlockProperties(this.block, this.targetBehavior);
        } catch (Exception exception) {
            InstantDeepslate.logger.error("Could not change block properties for " + this.block.getDescriptionId(), exception);
        }
    }

    public void disable() {
        if (this.defaultBehavior == null) return;
        try {
            this.setBlockProperties(this.block, this.defaultBehavior);
        } catch (Exception exception) {
            InstantDeepslate.logger.error("Could not revert block properties for " + this.block.getDescriptionId(), exception);
        }
    }

    private float[] getBlockProperties(Block block) throws Exception {
        // Get block properties field in BlockBehaviour
        Field propertiesField = BlockBehaviour.class.getDeclaredField(BlockTweak.properties);
        propertiesField.setAccessible(true);
        BlockBehaviour.Properties properties = (BlockBehaviour.Properties) propertiesField.get(block);
        // Get block state field in Block
        Field blockStateField = Block.class.getDeclaredField(BlockTweak.defaultBlockState);
        blockStateField.setAccessible(true);
        BlockState blockState = (BlockState) blockStateField.get(block);
        // Get fields
        Field altExplosionResistanceField = BlockBehaviour.class.getDeclaredField(BlockTweak.altExplosionResistance);
        Field explosionResistanceField = BlockBehaviour.Properties.class.getDeclaredField(BlockTweak.explosionResistance);
        Field destroyTimeField = BlockBehaviour.Properties.class.getDeclaredField(BlockTweak.destroyTime);
        Field destroySpeedField = BlockBehaviour.BlockStateBase.class.getDeclaredField(BlockTweak.destroySpeed);
        // Force accessible
        altExplosionResistanceField.setAccessible(true);
        explosionResistanceField.setAccessible(true);
        destroyTimeField.setAccessible(true);
        destroySpeedField.setAccessible(true);
        // Get values
        float altExplosionResistance = altExplosionResistanceField.getFloat(block);
        float explosionResistance = explosionResistanceField.getFloat(properties);
        float destroyTime = destroyTimeField.getFloat(properties);
        float destroySpeed = destroySpeedField.getFloat(blockState);
        // Return explosion resistance 1 & 2, and destroy time
        return new float[]{altExplosionResistance, explosionResistance, destroyTime, destroySpeed};
    }

    private void setBlockProperties(Block block, float[] targetProperties) throws Exception {
        // Get block properties field
        Field propertiesField = BlockBehaviour.class.getDeclaredField(BlockTweak.properties);
        propertiesField.setAccessible(true);
        BlockBehaviour.Properties properties = (BlockBehaviour.Properties) propertiesField.get(block);
        // Get block state field in Block
        Field blockStateField = Block.class.getDeclaredField(BlockTweak.defaultBlockState);
        blockStateField.setAccessible(true);
        BlockState blockState = (BlockState) blockStateField.get(block);
        // Get fields
        Field altExplosionResistance = BlockBehaviour.class.getDeclaredField(BlockTweak.altExplosionResistance);
        Field explosionResistance = BlockBehaviour.Properties.class.getDeclaredField(BlockTweak.explosionResistance);
        Field destroyTime = BlockBehaviour.Properties.class.getDeclaredField(BlockTweak.destroyTime);
        Field destroySpeed = BlockBehaviour.BlockStateBase.class.getDeclaredField(BlockTweak.destroySpeed);
        // Force accessible
        altExplosionResistance.setAccessible(true);
        explosionResistance.setAccessible(true);
        destroyTime.setAccessible(true);
        destroySpeed.setAccessible(true);
        // Set properties to target
        altExplosionResistance.set(block, targetProperties[0]);
        explosionResistance.set(properties, targetProperties[1]);
        destroyTime.set(properties, targetProperties[2]);
        destroySpeed.set(blockState, targetProperties[3]);
    }
}
